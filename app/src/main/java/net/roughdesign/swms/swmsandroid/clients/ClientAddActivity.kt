package net.roughdesign.swms.swmsandroid.clients

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.android.volley.VolleyError
import kotlinx.android.synthetic.main.client_add__content.*
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.web.WebErrorHandler
import net.roughdesign.swms.swmsandroid.web.repositories.JsonRepository
import net.roughdesign.swms.swmsandroid.web.repositories.ResponseReacter
import org.jetbrains.anko.contentView

class ClientAddActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ClientAddActivity::class.java)
            context.startActivity(intent)
        }
    }


    private lateinit var repo: JsonRepository<Client>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_add__activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        repo = Client.getRepository(this)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.client_add__menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.client_add_confirm_button -> {
                openAddConfirmationDialog()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    private fun openAddConfirmationDialog() {
        val name = client_name.text.toString()
        val contactData = client_contact.text.toString()

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
                WebErrorHandler.showFeedbackOnScreen(contentView!!, error)
            }
        })
    }
}
