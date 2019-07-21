package net.roughdesign.swms.swmsandroid.clients.activities.detail

import android.app.Activity
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.View
import com.android.volley.Response
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.utilities.web.errors.ViewErrorListener
import net.roughdesign.swms.swmsandroid.utilities.web.repositories.Repository

class ClientAddPresenter(
	private val activity: Activity,
	private val view: View,
	private val clientRepository: Repository<Client>,
	private var client: Client
){



	 fun showConfirmSaveDialog(name:String, contactData:String) {
		AlertDialog.Builder(activity)
			.setTitle("Confirmation")
			.setMessage("Save changes?")
			.setPositiveButton(android.R.string.yes) { dialog, which -> saveClient(name, contactData) }
			.setNegativeButton(android.R.string.no) { dialog, which -> }
			.show()
	}


	private fun saveClient(name:String, contactData:String) {
			val updatedClient = Client(client.id, name, contactData)
		val responseListener = Response.Listener<Client> {			reactToSuccessfulSaving(it)		}
		val errorListener = ViewErrorListener(view)
		clientRepository.save(updatedClient, responseListener, errorListener)

	}

	private fun reactToSuccessfulSaving(client: Client) {
		this.client=client
		Snackbar.make(
			view,
			"Saved successfully",
			Snackbar.LENGTH_LONG
		)
			.setAction("Action", null)
			.show()
	}




	 fun showDeleteConfirmDialog() {

		AlertDialog.Builder(activity)
			.setTitle("Confirmation")
			.setMessage("Delete client?")
			.setPositiveButton(android.R.string.yes) { dialog, which -> deleteClient() }
			.setNegativeButton(android.R.string.no) { dialog, which -> }
			.show()
	}


	fun deleteClient() {
		val id = client.id
		if (id == null) TODO("delete fail error message")

		val responseListener = Response.Listener<Boolean> {			reactToSuccessfulDeletion()		}
		val errorListener = ViewErrorListener(view)
		clientRepository.delete(id, responseListener, errorListener)
	}


	fun reactToSuccessfulDeletion(){
		Snackbar.make(
			view,
			"Deleted successfully",
			Snackbar.LENGTH_LONG
		)
			.setAction("Action", null)
			.show()
		activity.finish()
	}
}