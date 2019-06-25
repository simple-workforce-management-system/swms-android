package net.roughdesign.swms.swmsandroid.clients

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.web.JsonRepository
import net.roughdesign.swms.swmsandroid.web.ResponseReacter


class ClientListActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ClientListActivity::class.java)
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
        setContentView(R.layout.client_list_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        setUpListView()
        repository = Client.createRepository(this)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.client_list_menu, menu)
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

        recyclerView = findViewById<RecyclerView>(R.id.client_list_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }


    private fun requestClientListUpdate() {
        repository.index(object : ResponseReacter() {

            override fun onResponse(response: ByteArray) {

                val bytesAsString = String(response)
                val typeToken = object : TypeToken<ArrayList<Client>>() {}.type
                val clients = Gson().fromJson<ArrayList<Client>>(bytesAsString, typeToken)

                clientList.clear()
                clientList.addAll(clients)
                viewAdapter.notifyDataSetChanged()
            }

            override fun onErrorResponse(error: VolleyError) {
                Log.e(ClientListActivity::class.toString(), error.message.toString())

                TODO("not implemented")
            }
        })
    }
}
