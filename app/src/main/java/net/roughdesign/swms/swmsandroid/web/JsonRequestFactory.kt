package net.roughdesign.swms.swmsandroid.web

import com.android.volley.NetworkResponse
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL


class JsonRequestFactory(private val requestQueue: RequestQueue) {


    fun <T>sendRequest(apiAccess: ApiAccess, requestBody: String?, responseReacter: ResponseReacter<T>) {
        val method = apiAccess.method
        val url = apiAccess.url
        val request = create(method, url, requestBody, responseReacter)
        requestQueue.add(request)
    }
    fun <T>sendRequest(method: Int, url: URL, requestBody: String?, responseReacter: ResponseReacter<T>) {
        val request = create(method, url, requestBody, responseReacter)
        requestQueue.add(request)
    }

    fun <T> create(method: Int, url: URL, requestBody: String?, responseReacter: ResponseReacter<T>)
            : JsonRequest<T> {

        val listener = createResultListener(responseReacter)
        val errorListener = createErrorListener(responseReacter)
        val urlAsString = url.toString()
        return createJsonRequest(method, urlAsString, requestBody, listener, errorListener)
    }


    private fun <T> createResultListener(responseReacter: ResponseReacter<T>): Response.Listener<T> {

        return Response.Listener { response -> responseReacter.onResponse(response) }
    }


    private fun <T> createErrorListener(responseReacter: ResponseReacter<T>): Response.ErrorListener {

        return Response.ErrorListener { error -> responseReacter.onErrorResponse(error) }
    }


    private fun <T> createJsonRequest(
        method: Int, url: String,
        requestBody: String?,
        listener: Response.Listener<T>,
        errorListener: Response.ErrorListener
    ): JsonRequest<T> {

        return object : JsonRequest<T>(method, url, requestBody, listener, errorListener) {

            override fun parseNetworkResponse(response: NetworkResponse): Response<T> {
                val dataAsBytes = response.data
                val dataAsString = String(dataAsBytes)
                val typeToken = object : TypeToken<T>() {}.type
                val deserializedObject = Gson().fromJson<T>(dataAsString, typeToken)
                val cacheEntry = HttpHeaderParser.parseCacheHeaders(response)
                return Response.success(deserializedObject, cacheEntry)
            }
        }
    }


}
