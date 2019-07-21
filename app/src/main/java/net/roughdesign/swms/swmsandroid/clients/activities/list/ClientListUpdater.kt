package net.roughdesign.swms.swmsandroid.clients.activities.list

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.VolleyError
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.utilities.web.repositories.Repository
import net.roughdesign.swms.swmsandroid.utilities.web.errors.WebErrorHandler

class ClientListUpdater(
	private val repository: Repository<Client>,
	private val swiperefresh: SwipeRefreshLayout,
	private var recyclerView: RecyclerView,
	private val viewAdapter: RecyclerView.Adapter<*>,
	private val clientList: ArrayList<Client>
) {


	fun requestClientListUpdate() {

		swiperefresh.isRefreshing = true
		val responseListener = Response.Listener<List<Client>> { response -> respondToSuccess(response) }
		val errorListener = Response.ErrorListener { error -> respondToError(error) }
		repository.index(responseListener, errorListener)
	}


	private fun respondToSuccess(clients: List<Client>?) {
		if (clients == null) {
			TODO("Handle conversion error")
		}

		clientList.clear()
		clientList.addAll(clients)
		viewAdapter.notifyDataSetChanged()
		viewAdapter.notifyItemInserted(2)

		swiperefresh.isRefreshing = false
	}


	private fun respondToError(error: VolleyError?) {
		if (error != null) WebErrorHandler.showFeedbackOnScreen(recyclerView,error)
		swiperefresh.isRefreshing = false
	}
}