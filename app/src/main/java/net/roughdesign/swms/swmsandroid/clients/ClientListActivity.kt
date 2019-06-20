package net.roughdesign.swms.swmsandroid.clients

import android.os.Bundle
import android.os.Debug
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.client_list_activity.*
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.clients.models.ClientList
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class ClientListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_list_activity)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }



        val clientList = arrayListOf<Client>()

        viewAdapter = ClientListRecyclerAdapter(this, clientList)
        viewManager = LinearLayoutManager(this)

        recyclerView = findViewById<RecyclerView>(R.id.client_list_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }

        doAsync {
            val textResponse = URL("http://swmsapi.azurewebsites.net/clients").readText()

            val typeToken = object : TypeToken<List<Client>>() {}.type
            val clients = Gson().fromJson<ArrayList<Client>>(textResponse, typeToken)

            uiThread {

                Log.w("ClientListActivity", "Count: " + clients.count())
                clientList.clear()
                clientList.addAll(clients)
                viewAdapter.notifyDataSetChanged()
            }


        }

    }
}
