package net.roughdesign.swms.swmsandroid.utilities.web.requests

import com.android.volley.DefaultRetryPolicy
import net.roughdesign.swms.swmsandroid.users.authtokens.JsonWebToken


fun ConfigurableRequest.setAsAuthorizedJson(authToken: JsonWebToken) {
	bodyContentType = "application/json; charset=utf-8"
	headers["Content-Type"] = "application/json"
	headers["Authorization"] = "Bearer ${authToken.asString}"
	retryPolicy = DefaultRetryPolicy(2000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
}


fun ConfigurableRequest.setAsJsonRequest() {
	bodyContentType = "application/json; charset=utf-8"
	headers["Content-Type"] = "application/json"
}


fun ConfigurableRequest.setBearerAuthToken(authToken: JsonWebToken) {

	headers["Authorization"] = "Bearer ${authToken.asString}"
}


fun ConfigurableRequest.setRetryPolicy(initialTimeoutMs: Int, maxNumRetries: Int) {
	retryPolicy = DefaultRetryPolicy(initialTimeoutMs, maxNumRetries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
}
