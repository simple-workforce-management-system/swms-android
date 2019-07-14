package net.roughdesign.swms.swmsandroid.utilities.web.configurablerequests

import com.android.volley.DefaultRetryPolicy


fun ConfigurableRequest.setAsAuthorizedJson(authToken: String) {
	this.bodyContentType = "application/json; charset=utf-8"
	headers["Content-Type"] = "application/json"
	headers["Authorization"] = "Bearer $authToken"
	retryPolicy = DefaultRetryPolicy(2000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
}


fun ConfigurableRequest.setAsJsonRequest() {
	this.bodyContentType = "application/json; charset=utf-8"
	headers["Content-Type"] = "application/json"
}


fun ConfigurableRequest.setBearerAuthToken(authToken: String) {

	headers["Authorization"] = "Bearer $authToken"
}


fun ConfigurableRequest.setRetryPolicy(initialTimeoutMs: Int, maxNumRetries: Int) {
	retryPolicy = DefaultRetryPolicy(initialTimeoutMs, maxNumRetries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
}
