package net.roughdesign.swms.swmsandroid.clients

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.client_list_activity.*
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.web.JsonRepository
import net.roughdesign.swms.swmsandroid.web.ResponseReacter
import net.roughdesign.swms.swmsandroid.web.urlsets.UrlSet
import java.net.URL

class ClientListActivity : AppCompatActivity() {

    private val clientList = arrayListOf<Client>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var repository: JsonRepository<Client>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_list_activity)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { view ->
            ClientAddActivity.start(this)
        }

        setUpListView()
        repository = Client.createRepository(this)


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

                TODO("not implemented")
            }

        })
    }
}
