package net.roughdesign.swms.swmsandroid.web

import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import net.roughdesign.swms.swmsandroid.web.urlsets.ApiAccess


class JsonObjectRequest(apiAccess: ApiAccess, requestBody: String?, responseReacter: ResponseReacter) :
    JsonRequest<ByteArray>(
        apiAccess.method,
        apiAccess.url.toString(),
        requestBody,
        Response.Listener<ByteArray> { response -> responseReacter.onResponse(response) },
        Response.ErrorListener { error -> responseReacter.onErrorResponse(error) }
    ) {


    override fun parseNetworkResponse(response: NetworkResponse): Response<ByteArray> {
        val dataAsBytes: ByteArray = response.data
        val cacheEntry = HttpHeaderParser.parseCacheHeaders(response)
        return Response.success(dataAsBytes, cacheEntry)
    }


    @Throws(AuthFailureError::class)
    override fun getHeaders(): Map<String, String> {
        val params = HashMap<String, String>()
        params["Content-Type"] = "application/json"
        return params
    }
}