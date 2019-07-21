package net.roughdesign.swms.swmsandroid.clients.activities.add

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
import net.roughdesign.swms.swmsandroid.users.authtokens.AuthTokenRequest
import net.roughdesign.swms.swmsandroid.users.authtokens.JsonWebToken


class ClientAddActivity : AppCompatActivity() {


	private lateinit var clientAddPresenter: ClientAddPresenter


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.client_add__activity)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		val authTokenRequest = AuthTokenRequest(this)
		authTokenRequest.whenRequestFinished += { reactToAuthToken(it) }
		authTokenRequest.start()
	}


	private fun reactToAuthToken(jsonWebToken: JsonWebToken) {
		val clientRepository = ClientRepositoryFactory.create(this, jsonWebToken)
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
}

