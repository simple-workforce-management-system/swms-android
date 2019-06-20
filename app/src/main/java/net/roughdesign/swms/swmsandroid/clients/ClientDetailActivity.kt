package net.roughdesign.swms.swmsandroid.clients

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.client_detail_activity.*
import kotlinx.android.synthetic.main.client_detail_content.*
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.models.Client

class ClientDetailActivity : AppCompatActivity() {

    companion object {
        private const val clientExtraId = "ClientDetailActivity"

        fun start(context: Context, client: Client) {
            val intent = Intent(context, ClientDetailActivity::class.java)
            intent.putExtra(clientExtraId, client)
            context.startActivity(intent)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_detail_activity)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val client : Client = intent.getSerializableExtra(clientExtraId) as Client

        client_add_name.text.clear()
        client_add_name.text.append(client.name)

        client_add_contact.text.clear()
        client_add_contact.text.append(client.contactData)
    }

}
