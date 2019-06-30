package net.roughdesign.swms.swmsandroid.users

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.google.gson.Gson
import com.google.gson.JsonObject
import net.roughdesign.swms.swmsandroid.web.ServerConfiguration
import net.roughdesign.swms.swmsandroid.web.repositories.JsonObjectRequest
import net.roughdesign.swms.swmsandroid.web.repositories.JsonRepository
import net.roughdesign.swms.swmsandroid.web.repositories.ResponseReacter
import net.roughdesign.swms.swmsandroid.web.repositories.SwmsRequestQueue
import net.roughdesign.swms.swmsandroid.web.urlsets.ApiAccess
import java.net.URL

object SwmsLogin {

    private val apiAccess: ApiAccess


    init {

        val url = URL(ServerConfiguration.url, "users/authenticate/")
        apiAccess = ApiAccess(Request.Method.POST, url)
    }


    fun Login(context: Context, userName: String, password: String, responseReacter: ResponseReacter) {

        Log.i(JsonRepository::class.java.simpleName, "Requesting " + apiAccess.url)
        val requestBody = createRequestBody(userName, password)
        val request = createRequest(requestBody, responseReacter)
        val requestQueue = SwmsRequestQueue.getRequestQueue(context)
        requestQueue.add(request)
    }


    private fun createRequestBody(userName: String, password: String): String? {
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", userName)
        jsonObject.addProperty("password", password)
        return Gson().toJson(jsonObject)
    }

    private fun createRequest(requestBody: String?, responseReacter: ResponseReacter): JsonObjectRequest {
        val request = JsonObjectRequest(apiAccess, requestBody, responseReacter)
        val retryPolicy = DefaultRetryPolicy(3000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        request.retryPolicy = retryPolicy
        return request
    }
}