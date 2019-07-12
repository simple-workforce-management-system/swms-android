package net.roughdesign.swms.swmsandroid.web.repositories

import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class StringRequestWithHeaders(
	method: Int,
	url: String?,
	private val _bodyContentType: String,
	private val _headers: Map<String, String>,
	private val mRequestBody: String?,
	listener: Response.Listener<String>?,
	errorListener: Response.ErrorListener?
) : StringRequest(method, url, listener, errorListener) {

	init {
		val retryPolicy = DefaultRetryPolicy(3000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
		setRetryPolicy(retryPolicy)
	}


	override fun getBodyContentType(): String {
		return _bodyContentType
	}

	@Throws(AuthFailureError::class)
	override fun getHeaders(): Map<String, String> {
		return _headers
	}
}