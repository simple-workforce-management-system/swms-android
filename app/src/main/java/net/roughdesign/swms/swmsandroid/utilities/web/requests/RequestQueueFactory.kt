package net.roughdesign.swms.swmsandroid.utilities.web.requests

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object RequestQueueFactory {

	private var requestQueue: RequestQueue? = null

	fun getOrCreate(context: Context): RequestQueue {

		return requestQueue ?: synchronized(this) {
			requestQueue
				?: Volley.newRequestQueue(context.applicationContext).also { requestQueue = it }
		}
	}
}