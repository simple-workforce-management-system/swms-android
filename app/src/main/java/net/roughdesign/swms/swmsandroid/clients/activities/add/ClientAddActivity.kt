package net.roughdesign.swms.swmsandroid.clients.activities.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.client_add__content.*
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.clients.web.ClientRepositoryFactory
import net.roughdesign.swms.swmsandroid.utilities.dependencyinjection.DI

class ClientAddActivity : AppCompatActivity() {


	private lateinit var clientAddPresenter: ClientAddPresenter


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.client_add__activity)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)


		val clientRepositoryFactory = DI.resolve(ClientRepositoryFactory::class.java)
		val authToken = intent.getStringExtra(ARG_AUTH_TOKEN)
		val clientRepository = clientRepositoryFactory.create(this, authToken)
		val view = findViewById<View>(R.id.activity_view)

		clientAddPresenter = ClientAddPresenter(this, clientRepository, view)
	}


	override fun onCreateOptionsMenu(menu: Menu): Boolean {

		val inflater: MenuInflater = menuInflater
		inflater.inflate(R.menu.client_add__menu, menu)
		return true
	}


	override fun onOptionsItemSelected(item: MenuItem): Boolean {

		if (item.itemId == android.R.id.home) {
			onBackPressed()
			return true
		}
		if (item.itemId == R.id.client_add_confirm_button) return clientAddPresenter.openAddConfirmationDialog()
		return super.onOptionsItemSelected(item)
	}


	fun createClientFromForm(): Client {
		val name = client_name.text.toString()
		val contactData = client_contact.text.toString()
		return Client(null, name, contactData)
	}


	companion object {
		const val ARG_AUTH_TOKEN = "ARG_AUTH_TOKEN"

		fun start(context: Context, authToken: String) {
			val intent = Intent(context, ClientAddActivity::class.java)
			intent.putExtra(ARG_AUTH_TOKEN, authToken)
			context.startActivity(intent)
		}
	}
}

