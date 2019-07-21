package net.roughdesign.swms.swmsandroid.clients.activities.list

import android.app.Activity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.users.authtokens.JsonWebToken
import net.roughdesign.swms.swmsandroid.utilities.web.repositories.Repository

object ClientListUpdaterFactory {

	fun create(
		repository: Repository<Client>,
		activity: Activity,
		clientListList: RecyclerView,
		swiperefresh: SwipeRefreshLayout,
		authToken: JsonWebToken
	): ClientListUpdater {

		val clientList = arrayListOf<Client>()
		val viewAdapter = ClientListRecyclerAdapter(activity, authToken, clientList)
		val viewManager = LinearLayoutManager(activity)

		val recyclerView = clientListList.apply {
			setHasFixedSize(true)
			layoutManager = viewManager
			adapter = viewAdapter
		}

		return ClientListUpdater(repository, swiperefresh, recyclerView, viewAdapter, clientList)
	}
}