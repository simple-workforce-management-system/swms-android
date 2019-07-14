package net.roughdesign.swms.swmsandroid.users

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.google.gson.Gson
import com.google.gson.JsonObject
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.utilities.web.repositories.JsonObjectRequest
import net.roughdesign.swms.swmsandroid.utilities.web.repositories.ResponseReacter
import net.roughdesign.swms.swmsandroid.utilities.web.repositories.SwmsRequestQueue
import net.roughdesign.swms.swmsandroid.utilities.web.urlsets.ApiAccess
import java.net.URL

object SwmsLogin {


	fun login(context: Context, userName: String, password: String, responseReacter: ResponseReacter) {

		val baseUrl = URL(context.getString(R.string.config__web__server_address))
		val url = URL(baseUrl, "users/authenticate")

		Log.i(SwmsLogin::class.java.simpleName, "Requesting $url")
		val apiAccess = ApiAccess(Request.Method.POST, url)


		val requestBody = createRequestBody(userName, password)
		val request = createRequest(apiAccess, requestBody, responseReacter)
		val requestQueue = SwmsRequestQueue.getRequestQueue(context)
		requestQueue.add(request)
	}


	private fun createRequestBody(userName: String, password: String): String? {
		val jsonObject = JsonObject()
		jsonObject.addProperty("username", userName)
		jsonObject.addProperty("password", password)
		return Gson().toJson(jsonObject)
	}


	private fun createRequest(
		apiAccess: ApiAccess,
		requestBody: String?,
		responseReacter: ResponseReacter
	): JsonObjectRequest {
		val request = JsonObjectRequest(
			apiAccess,
			requestBody,
			responseReacter
		)
		val retryPolicy = DefaultRetryPolicy(3000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
		request.retryPolicy = retryPolicy
		return request
	}

	fun getAuthToken(username: String, password: String): String {
		TODO("not implemented")
	}
}