package net.roughdesign.swms.swmsandroid.web

import com.android.volley.VolleyError

abstract class ResponseReacter {

    abstract fun onResponse(response: ByteArray)

    abstract fun onErrorResponse(error: VolleyError)
}
