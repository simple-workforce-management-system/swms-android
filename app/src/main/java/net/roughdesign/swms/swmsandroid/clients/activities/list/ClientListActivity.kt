package net.roughdesign.swms.swmsandroid.clients.activities.list

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.client_list__content.*
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.activities.add.ClientAddActivity
import net.roughdesign.swms.swmsandroid.clients.web.ClientRepositoryFactory
import net.roughdesign.swms.swmsandroid.users.authtokens.AuthTokenRequest
import net.roughdesign.swms.swmsandroid.users.authtokens.JsonWebToken


class ClientListActivity : AppCompatActivity() {


	private var clientListUpdater: ClientListUpdater? = null


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.client_list__activity)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		val authTokenRequest = AuthTokenRequest(this)
		authTokenRequest.whenRequestFinished += { reactToAuthToken(it) }
		authTokenRequest.start()
	}


	override fun onCreateOptionsMenu(menu: Menu): Boolean {

		val inflater: MenuInflater = menuInflater
		inflater.inflate(R.menu.client_list__menu, menu)
		return true
	}


	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			android.R.id.home -> {
				onBackPressed()
				true
			}
			R.id.client_list_add -> {
				val intent = Intent(this, ClientAddActivity::class.java)
				startActivity(intent)
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}


	public override fun onResume() {
		super.onResume()
		clientListUpdater?.requestClientListUpdate()
	}


	private fun reactToAuthToken(authToken: JsonWebToken) {
		val repository = ClientRepositoryFactory.create(this, authToken)
		clientListUpdater = ClientListUpdaterFactory.create(repository, this, client_list_list, swiperefresh, authToken)
		swiperefresh.setOnRefreshListener { clientListUpdater?.requestClientListUpdate() }
		clientListUpdater?.requestClientListUpdate()
	}
}

