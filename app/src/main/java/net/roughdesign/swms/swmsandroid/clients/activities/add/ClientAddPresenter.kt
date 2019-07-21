package net.roughdesign.swms.swmsandroid.clients.activities.add

import android.support.design.widget.Snackbar
import android.view.View
import com.android.volley.Response
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.utilities.web.errors.ViewErrorListener
import net.roughdesign.swms.swmsandroid.utilities.web.repositories.Repository

class ClientAddPresenter(
	private val activity: ClientAddActivity,
	private val clientRepository: Repository<Client>,
	private val view: View
) {


	fun openAddConfirmationDialog(): Boolean {
		val client = activity.createClientFromForm()
		val listener = Response.Listener<Client> { response -> reactToPositiveResponse() }
		val errorListener = ViewErrorListener(view)
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