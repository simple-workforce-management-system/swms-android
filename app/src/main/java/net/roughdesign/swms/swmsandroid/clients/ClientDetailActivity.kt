package net.roughdesign.swms.swmsandroid.clients

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast
import com.android.volley.VolleyError

import kotlinx.android.synthetic.main.client_detail_activity.*
import kotlinx.android.synthetic.main.client_detail_activity.fab
import kotlinx.android.synthetic.main.client_detail_activity.toolbar
import kotlinx.android.synthetic.main.client_detail_content.*
import kotlinx.android.synthetic.main.client_list_activity.*
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.web.JsonRepository
import net.roughdesign.swms.swmsandroid.web.ResponseReacter

class ClientDetailActivity : AppCompatActivity() {

    companion object {
        private const val clientExtraId = "ClientDetailActivity"

        fun start(context: Context, client: Client) {
            val intent = Intent(context, ClientDetailActivity::class.java)
            intent.putExtra(clientExtraId, client)
            context.startActivity(intent)
        }

    }

    private lateinit var repository: JsonRepository<Client>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_detail_activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repository = Client.createRepository(this)

        val client: Client = intent.getSerializableExtra(clientExtraId) as Client
        client_add_name.text.clear()
        client_add_name.text.append(client.name)
        client_add_contact.text.clear()
        client_add_contact.text.append(client.contactData)

        fab.setOnClickListener { view ->

            AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Save changes?")

                .setPositiveButton(android.R.string.yes) { dialog, which ->

                    val name = client_add_name.text.toString()
                    val contactData = client_add_contact.text.toString()
                    val updatedClient = Client(client.id, name, contactData)

                    repository.update(updatedClient, object : ResponseReacter() {
                        override fun onResponse(response: ByteArray) {
                            Snackbar.make(view, "Saved successfully", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()
                        }

                        override fun onErrorResponse(error: VolleyError) {
                            TODO("not implemented")
                        }
                    })

                    Toast.makeText(
                        applicationContext,
                        android.R.string.yes, Toast.LENGTH_SHORT
                    ).show()
                }

                .setNegativeButton(android.R.string.no) { dialog, which ->

                }

                .show()
        }


    }

}
