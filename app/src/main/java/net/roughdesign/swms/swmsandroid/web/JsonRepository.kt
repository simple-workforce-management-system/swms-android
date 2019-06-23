package net.roughdesign.swms.swmsandroid.web

import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
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

    fun delete(model: T, responseReacter: ResponseReacter) {
        val apiAccess = urlSet.delete
        val requestBody = Gson().toJson(model)
        sendRequest(apiAccess, requestBody, responseReacter)
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
        val method = apiAccess.method
        val url = apiAccess.url
        val urlAsString = url.toString()

        val listener = Response.Listener<ByteArray> { response -> responseReacter.onResponse(response) }
        val errorListener = Response.ErrorListener { error -> responseReacter.onErrorResponse(error) }

        val request = object : JsonRequest<ByteArray>(method, urlAsString, requestBody, listener, errorListener) {

            override fun parseNetworkResponse(response: NetworkResponse): Response<ByteArray> {
                val dataAsBytes: ByteArray = response.data
                val cacheEntry = HttpHeaderParser.parseCacheHeaders(response)
                return Response.success(dataAsBytes, cacheEntry)
            }
        }

        requestQueue.add(request)
    }

}



