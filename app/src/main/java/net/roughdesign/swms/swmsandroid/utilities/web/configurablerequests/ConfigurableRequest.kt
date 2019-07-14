package net.roughdesign.swms.swmsandroid.utilities.web.configurablerequests

import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import net.roughdesign.swms.swmsandroid.utilities.events.EventI
import java.net.URL

class ConfigurableRequest(method: Int, url: URL, errorListener: Response.ErrorListener?) :
	Request<ByteArray>(method, url.toString(), errorListener) {


	val headers = HashMap<String, String>()
	private var bodyContentType : String? = null
	private	var ownBody:ByteArray? = ByteArray(0)

	val whenFinished = EventI<ByteArray?>()


	@Throws(AuthFailureError::class)
	override fun getHeaders(): Map<String, String> {
		return headers
	}


	override fun getBodyContentType(): String {
		val contentType = this.bodyContentType
		return when {
			contentType != null -> contentType
			else -> super.getBodyContentType()
		}
	}

	fun setBodyContentType(bodyContentType:String){
		this.bodyContentType = bodyContentType
	}

	override fun getBody(): ByteArray? {
		return ownBody
	}

	fun setRequestBody(byteArray:ByteArray?){
		ownBody = byteArray
	}


	override fun parseNetworkResponse(response: NetworkResponse?): Response<ByteArray?> {
		return Response.success(
			response?.data,
			HttpHeaderParser.parseCacheHeaders(response)
		)
	}


	override fun deliverResponse(response: ByteArray?) {
		whenFinished.invoke(response)
	}
}