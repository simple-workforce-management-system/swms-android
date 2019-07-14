package net.roughdesign.swms.swmsandroid.clients.activities.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.client_list__content.*
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.activities.add.ClientAddActivity
import net.roughdesign.swms.swmsandroid.clients.web.ClientRepositoryFactory
import net.roughdesign.swms.swmsandroid.clients.web.ClientRepository
import net.roughdesign.swms.swmsandroid.utilities.dependencyinjection.DI


class ClientListActivity : AppCompatActivity() {


	private lateinit var authToken: String
	private lateinit var repository: ClientRepository

	private lateinit var clientListUpdater: ClientListUpdater


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.client_list__activity)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		val clientRepositoryFactory = DI.resolve(ClientRepositoryFactory::class.java)
		val clientListUpdaterFactory = DI.resolve(ClientListUpdaterFactory::class.java)

		authToken = intent.getStringExtra(ARG_AUTH_TOKEN)
		repository = clientRepositoryFactory.create(this, authToken)
		clientListUpdater = clientListUpdaterFactory.create(repository, this, client_list_list, swiperefresh)
		swiperefresh.setOnRefreshListener { clientListUpdater.requestClientListUpdate() }
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
				ClientAddActivity.start(this, authToken)
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}


	public override fun onResume() {
		super.onResume()
		clientListUpdater.requestClientListUpdate()
	}


	companion object {
		private const val ARG_AUTH_TOKEN = "ARG_AUTH_TOKEN"

		fun start(context: Context, authToken: String) {
			val intent = Intent(context, ClientListActivity::class.java)
			intent.putExtra(ARG_AUTH_TOKEN, authToken)
			context.startActivity(intent)
		}
	}
}

