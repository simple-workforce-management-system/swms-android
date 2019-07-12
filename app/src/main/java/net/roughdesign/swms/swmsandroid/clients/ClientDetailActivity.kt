package net.roughdesign.swms.swmsandroid.clients

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.android.volley.VolleyError
import kotlinx.android.synthetic.main.client_detail__content.*
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.web.WebErrorHandler
import net.roughdesign.swms.swmsandroid.web.repositories.JsonRepository
import net.roughdesign.swms.swmsandroid.web.repositories.ResponseReacter


class ClientDetailActivity : AppCompatActivity() {

	companion object {
		private const val clientExtraId = "ClientDetailActivity"

		fun start(activity: Activity, sourceView: View, client: Client) {
			val intent = Intent(activity, ClientDetailActivity::class.java)
			intent.putExtra(clientExtraId, client)

			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
				val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sourceView, "client_add")
				activity.startActivity(intent, options.toBundle())
			} else {
				activity.startActivity(intent)
			}
		}
	}

	private lateinit var repository: JsonRepository<Client>
	private lateinit var client: Client


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.client_detail__activity)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)


		repository = Client.getRepository(this)

		client = intent.getSerializableExtra(clientExtraId) as Client
		client_name.text.clear()
		client_name.text.append(client.name)
		client_contact.text.clear()
		client_contact.text.append(client.contactData)
	}


	override fun onCreateOptionsMenu(menu: Menu): Boolean {

		val inflater: MenuInflater = menuInflater
		inflater.inflate(R.menu.client_detail__menu, menu)
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
				showDeleteConfirmDialog()
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
			.setPositiveButton(android.R.string.yes) { dialog, which -> saveClient() }
			.setNegativeButton(android.R.string.no) { dialog, which -> }
			.show()
	}


	private fun saveClient() {
		val name = client_name.text.toString()
		val contactData = client_contact.text.toString()
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
				WebErrorHandler.showFeedbackOnScreen(findViewById(R.id.activity_view), error)
			}
		})
	}


	private fun showDeleteConfirmDialog() {

		AlertDialog.Builder(this)
			.setTitle("Confirmation")
			.setMessage("Delete client?")
			.setPositiveButton(android.R.string.yes) { dialog, which -> deleteClient() }
			.setNegativeButton(android.R.string.no) { dialog, which -> }
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
				WebErrorHandler.showFeedbackOnScreen(findViewById(R.id.activity_view), error)
			}
		})
	}
}