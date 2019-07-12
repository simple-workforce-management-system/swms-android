package net.roughdesign.swms.swmsandroid.web.repositories

import android.util.Log
import com.android.volley.*
import com.google.gson.Gson
import net.roughdesign.swms.swmsandroid.web.urlsets.ApiAccess
import net.roughdesign.swms.swmsandroid.web.urlsets.UrlSet

class JsonRepository<T>(
    private val urlSet: UrlSet,
    private val requestQueue: RequestQueue
) {


    fun create(model: T, responseReacter: ResponseReacter) {
        val apiAccess = urlSet.create
        val requestBody = Gson().toJson(model)
        sendRequest(apiAccess, requestBody, responseReacter)
    }

    fun update(model: T, responseReacter: ResponseReacter) {
        val apiAccess = urlSet.update
        val requestBody = Gson().toJson(model)
        sendRequest(apiAccess, requestBody, responseReacter)
    }

    fun delete(id: Long, responseReacter: ResponseReacter) {
        val apiAccess = urlSet.delete
        val apiAccessWithParameter = apiAccess.getFullUrl(id.toString())
        sendRequest(apiAccessWithParameter, null, responseReacter)
    }

    fun index(responseReacter: ResponseReacter) {
        val apiAccess = urlSet.index
        sendRequest(apiAccess, null, responseReacter)
    }

    fun get(id: Long, responseReacter: ResponseReacter) {
        val apiAccessWithParameter = urlSet.get
        val apiAccessWithId = apiAccessWithParameter.getFullUrl(id.toString())
        sendRequest(apiAccessWithId, null, responseReacter)
    }


    private fun sendRequest(apiAccess: ApiAccess, requestBody: String?, responseReacter: ResponseReacter) {

        Log.i(JsonRepository::class.java.simpleName, "Requesting " + apiAccess.url)
        val request = JsonObjectRequest(apiAccess, requestBody, responseReacter)
        val retryPolicy = DefaultRetryPolicy(3000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        request.retryPolicy = retryPolicy
        requestQueue.add(request)
    }
}




