package net.roughdesign.swms.swmsandroid.web.repositories

import com.android.volley.VolleyError

abstract class StringResponseReacter {

	abstract fun onResponse(response: String)

	abstract fun onErrorResponse(error: VolleyError)
}