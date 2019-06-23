package net.roughdesign.swms.swmsandroid.clients

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.android.volley.VolleyError
import net.roughdesign.swms.swmsandroid.R

import kotlinx.android.synthetic.main.client_add_activity.*
import kotlinx.android.synthetic.main.client_add_content.*
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.web.ResponseReacter
import org.jetbrains.anko.contentView
import java.net.URL

class ClientAddActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ClientAddActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_add_activity)
        setSupportActionBar(toolbar)

        val nameInput = client_add_name
        val contactDataInput = client_add_contact

        val repo = Client.createRepository(this)

        client_add_confirm.setOnClickListener {
            val name = nameInput.text.toString()
            val contactData = contactDataInput.text.toString()

            val client = Client(0, name, contactData)
            repo.create(client, object : ResponseReacter() {

                override fun onResponse(response: ByteArray) {
                    Snackbar.make(
                        findViewById(R.id.activity_view),
                        "Saved successfully", Snackbar.LENGTH_LONG
                    )
                        .setAction("Action", null).show()
                    finish()
                }

                override fun onErrorResponse(error: VolleyError) {
                    TODO("not implemented")
                }

            })


            //val bla = URL("<api call>").

        }
    }

}
