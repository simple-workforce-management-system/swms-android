package net.roughdesign.swms.swmsandroid.clients.activities.add

import android.support.design.widget.Snackbar
import android.view.View
import com.android.volley.Response
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.clients.web.ClientRepository
import net.roughdesign.swms.swmsandroid.utilities.web.DefaultErrorListener

class ClientAddPresenter(
	private val activity: ClientAddActivity,
	private val clientRepository: ClientRepository,
	private val view: View
) {


	fun openAddConfirmationDialog(): Boolean {
		val client = activity.createClientFromForm()
		val listener = Response.Listener<Client> { response -> reactToPositiveResponse() }
		val errorListener = DefaultErrorListener(view)
		clientRepository.create(client, listener, errorListener)
		return true
	}


	private fun reactToPositiveResponse() {
		Snackbar.make(
			view,
			"Saved successfully",
			Snackbar.LENGTH_LONG
		)
			.setAction("Action", null)
			.show()
		activity.finish()
	}
}