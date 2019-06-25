package net.roughdesign.swms.swmsandroid.clients

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.VolleyError
import kotlinx.android.synthetic.main.client_detail_activity.*
import kotlinx.android.synthetic.main.client_detail_content.*
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
    private lateinit var client: Client


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_detail_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        repository = Client.createRepository(this)

        client = intent.getSerializableExtra(clientExtraId) as Client
        client_add_name.text.clear()
        client_add_name.text.append(client.name)
        client_add_contact.text.clear()
        client_add_contact.text.append(client.contactData)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.client_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.client_detail_save_button -> {
                showConfirmSaveDialog()
                true
            }
            R.id.client_detail_delete_button -> {
                deleteClient()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    private fun showConfirmSaveDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirmation")
            .setMessage("Save changes?")

            .setPositiveButton(android.R.string.yes) { dialog, which ->

                val name = client_add_name.text.toString()
                val contactData = client_add_contact.text.toString()
                val updatedClient = Client(client.id, name, contactData)

                repository.update(updatedClient, object : ResponseReacter() {
                    override fun onResponse(response: ByteArray) {
                        Snackbar.make(
                            findViewById(R.id.activity_view),
                            "Saved successfully", Snackbar.LENGTH_LONG
                        )
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


    private fun deleteClient() {
        repository.delete(client.id, object : ResponseReacter() {
            override fun onResponse(response: ByteArray) {
                Snackbar.make(
                    findViewById(R.id.activity_view),
                    "Deleted successfully", Snackbar.LENGTH_LONG
                )
                    .setAction("Action", null).show()
                finish()
            }

            override fun onErrorResponse(error: VolleyError) {
                Log.e(ClientDetailActivity::class.toString(), error.message.toString())
                TODO("not implemented")
            }
        })
    }
}