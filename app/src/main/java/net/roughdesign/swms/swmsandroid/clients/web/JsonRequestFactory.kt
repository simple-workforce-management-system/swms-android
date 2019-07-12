package net.roughdesign.swms.swmsandroid.clients.web

import com.android.volley.Response
import net.roughdesign.swms.swmsandroid.web.repositories.StringRequestWithHeaders
import net.roughdesign.swms.swmsandroid.web.repositories.StringResponseReacter
import java.net.URL

class JsonRequestFactory(private val _authToken: String) {


    fun createRequest(
        _method: Int, _url: URL, _requestBody: String?, _responseReacter: StringResponseReacter
    ): StringRequestWithHeaders {
        val url = _url.toString()
        val bodyContentType = "application/json; charset=utf-8"
        val headers = createDefaultJsonHeaders()
        val listener = Response.Listener<String> { _responseReacter.onResponse(it) }
        val errorListener = Response.ErrorListener { _responseReacter.onErrorResponse(it) }

        return StringRequestWithHeaders(
            _method,
            url,
            bodyContentType,
            headers,
            _requestBody,
            listener,
            errorListener
        )
    }


    private fun createDefaultJsonHeaders(): HashMap<String, String> {
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"
        headers["Authorization"] = "Bearer $_authToken"
        return headers
    }
}