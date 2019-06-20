package net.roughdesign.swms.swmsandroid.web

import com.android.volley.VolleyError

abstract class ResponseReacter<T> {

    abstract fun onResponse(response: T)

    abstract fun onErrorResponse(error: VolleyError)
}
