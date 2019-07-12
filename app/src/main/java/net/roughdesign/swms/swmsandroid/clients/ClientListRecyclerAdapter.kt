package net.roughdesign.swms.swmsandroid.clients

import android.app.Activity
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.models.Client

class ClientListRecyclerAdapter(val activity: Activity, private val clients: List<Client>) :
	RecyclerView.Adapter<ClientListRecyclerAdapter.ViewHolder>() {

	class ViewHolder(val constraintLayout: ConstraintLayout) : RecyclerView.ViewHolder(constraintLayout)


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val layoutInflater = LayoutInflater.from(parent.context)
		val constraintLayout = layoutInflater.inflate(R.layout.client_list__row, parent, false) as ConstraintLayout
		return ViewHolder(constraintLayout)
	}


	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		val client = clients[position]
		val constraintLayout = holder.constraintLayout
		val title = constraintLayout.getViewById(R.id.row_title) as TextView
		title.text = client.name

		constraintLayout.setOnClickListener {
			ClientDetailActivity.start(activity, holder.constraintLayout, client)
		}
	}

	override fun getItemCount() = clients.size
}