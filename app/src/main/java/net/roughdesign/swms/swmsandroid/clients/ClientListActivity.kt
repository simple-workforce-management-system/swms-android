package net.roughdesign.swms.swmsandroid.clients

import android.accounts.Account
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.client_list__content.*
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.web.WebErrorHandler
import net.roughdesign.swms.swmsandroid.web.repositories.JsonRepository
import net.roughdesign.swms.swmsandroid.web.repositories.ResponseReacter


class ClientListActivity : AppCompatActivity() {

	companion object {
		private const val ARG_ACCOUNT = "ARG_ACCOUNT"

		fun start(context: Context, account: Account) {
			val intent = Intent(context, ClientListActivity::class.java)
			intent.putExtra(ARG_ACCOUNT, account)
			context.startActivity(intent)
		}
	}

	private val clientList = arrayListOf<Client>()

	private lateinit var recyclerView: RecyclerView
	private lateinit var viewAdapter: RecyclerView.Adapter<*>
	private lateinit var viewManager: RecyclerView.LayoutManager

	private lateinit var repository: JsonRepository<Client>


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.client_list__activity)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		var account = intent.getParcelableExtra<Account>(ARG_ACCOUNT)



		setUpListView()
		repository = Client.getRepository(this)
	}


	override fun onCreateOptionsMenu(menu: Menu): Boolean {

		val inflater: MenuInflater = menuInflater
		inflater.inflate(R.menu.client_list__menu, menu)
		return true
	}


	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			android.R.id.home -> {
				onBackPressed()
				true
			}
			R.id.client_list_add -> {
				ClientAddActivity.start(this)
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}


	public override fun onResume() {
		super.onResume()
		requestClientListUpdate()
	}


	private fun setUpListView() {
		viewAdapter = ClientListRecyclerAdapter(this, clientList)
		viewManager = LinearLayoutManager(this)

		recyclerView = client_list_list.apply {
			setHasFixedSize(true)
			layoutManager = viewManager
			adapter = viewAdapter
		}

		swiperefresh.setOnRefreshListener { requestClientListUpdate() }
	}


	private fun requestClientListUpdate() {

		swiperefresh.isRefreshing = true
		repository.index(object : ResponseReacter() {

			override fun onResponse(response: ByteArray) {

				val bytesAsString = String(response)
				val typeToken = object : TypeToken<ArrayList<Client>>() {}.type
				val clients = Gson().fromJson<ArrayList<Client>>(bytesAsString, typeToken)

				clientList.clear()
				clientList.addAll(clients)
				viewAdapter.notifyDataSetChanged()
				viewAdapter.notifyItemInserted(2)

				swiperefresh.isRefreshing = false
			}

			override fun onErrorResponse(error: VolleyError) {
				WebErrorHandler.showFeedbackOnScreen(recyclerView, error)
				swiperefresh.isRefreshing = false
			}
		})
	}
}
