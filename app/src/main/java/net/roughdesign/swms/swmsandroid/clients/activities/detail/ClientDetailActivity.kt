package net.roughdesign.swms.swmsandroid.clients.activities.detail

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.client_detail__activity.*
import kotlinx.android.synthetic.main.client_detail__content.*
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.clients.web.ClientRepositoryFactory
import net.roughdesign.swms.swmsandroid.utilities.dependencyinjection.DI


class ClientDetailActivity : AppCompatActivity() {

	private lateinit var clientAddPresenter: ClientAddPresenter


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.client_detail__activity)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		val authToken: String = intent.getStringExtra(ARG_AUTH_TOKEN)
		val clientRepositoryFactory = DI.resolve(ClientRepositoryFactory::class.java)
		val clientRepository = clientRepositoryFactory.create(this, authToken)
		val client = intent.getSerializableExtra(ARG_CLIENT) as Client
		clientAddPresenter = ClientAddPresenter(this, activity_view, clientRepository, client)

		client_name.setText(client.name)
		client_contact.setText(client.contactData)
	}


	override fun onCreateOptionsMenu(menu: Menu): Boolean {

		val inflater: MenuInflater = menuInflater
		inflater.inflate(R.menu.client_detail__menu, menu)
		return true
	}


	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			android.R.id.home -> onBackPressed()
			R.id.client_detail_save_button -> showConfirmSaveDialog()
			R.id.client_detail_delete_button -> clientAddPresenter.showDeleteConfirmDialog()
			else -> return super.onOptionsItemSelected(item)
		}
		return true
	}


	private fun showConfirmSaveDialog() {

		val name = client_name.text.toString()
		val contactData = client_contact.text.toString()
		clientAddPresenter.showConfirmSaveDialog(name, contactData)
	}


	companion object {
		private const val ARG_AUTH_TOKEN = "ARG_AUTH_TOKEN"
		private const val ARG_CLIENT = "ClientDetailActivity"

		fun start(activity: Activity, sourceView: View, authToken: String, client: Client) {
			val intent = Intent(activity, ClientDetailActivity::class.java)
			intent.putExtra(ARG_AUTH_TOKEN, authToken)
			intent.putExtra(ARG_CLIENT, client)

			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
				val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sourceView, "client_add")
				activity.startActivity(intent, options.toBundle())
			} else {
				activity.startActivity(intent)
			}
		}
	}
}

