package net.roughdesign.swms.swmsandroid.utilities.web.repositories

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import net.roughdesign.swms.swmsandroid.users.authtokens.JsonWebToken
import net.roughdesign.swms.swmsandroid.utilities.web.requests.ConfigurableRequest
import net.roughdesign.swms.swmsandroid.utilities.web.requests.setAsAuthorizedJson
import java.net.URL


class Repository<T>(
	private val baseUrl: URL,
	private val authToken: JsonWebToken,
	private val jsonConverter: JsonConverter<T>,
	private val requestQueue: RequestQueue
) {


	fun create(user: T, listener: Response.Listener<T>, errorListener: Response.ErrorListener?) {

		val method = Request.Method.POST
		val url = URL(baseUrl, "create/")
		val configurableRequest = ConfigurableRequest(method, url, errorListener)

		configurableRequest.setAsAuthorizedJson(authToken)
		val requestBody = jsonConverter.serialize(user)
		configurableRequest.setRequestBody(requestBody)
		configurableRequest.whenFinished += { listener.onResponse(jsonConverter.deserialize(it)) }
		requestQueue.add(configurableRequest)
	}


	fun save(user: T, listener: Response.Listener<T>, errorListener: Response.ErrorListener?) {

		val method = Request.Method.PUT
		val url = URL(baseUrl, "update/")
		val configurableRequest = ConfigurableRequest(method, url, errorListener)

		configurableRequest.setAsAuthorizedJson(authToken)
		val requestBody = jsonConverter.serialize(user)
		configurableRequest.setRequestBody(requestBody)
		configurableRequest.whenFinished += { listener.onResponse(jsonConverter.deserialize(it)) }
		requestQueue.add(configurableRequest)
	}


	fun delete(id: Long, listener: Response.Listener<Boolean>, errorListener: Response.ErrorListener?) {

		val method = Request.Method.DELETE
		val url = URL(baseUrl, "delete/$id")
		val configurableRequest = ConfigurableRequest(method, url, errorListener)

		configurableRequest.setAsAuthorizedJson(authToken)
		configurableRequest.whenFinished += { listener.onResponse(it != null) }
		requestQueue.add(configurableRequest)
	}


	fun index(listener: Response.Listener<List<T>>, errorListener: Response.ErrorListener?) {

		val method = Request.Method.GET
		val configurableRequest = ConfigurableRequest(method, baseUrl, errorListener)

		configurableRequest.setAsAuthorizedJson(authToken)
		configurableRequest.whenFinished += { listener.onResponse(jsonConverter.deserializeList(it)) }
		requestQueue.add(configurableRequest)
	}


	fun get(id: Long, listener: Response.Listener<T>, errorListener: Response.ErrorListener?) {

		val method = Request.Method.GET
		val url = URL(baseUrl, "start/$id")
		val configurableRequest = ConfigurableRequest(method, url, errorListener)

		configurableRequest.setAsAuthorizedJson(authToken)
		configurableRequest.whenFinished += { listener.onResponse(jsonConverter.deserialize(it)) }
		requestQueue.add(configurableRequest)
	}
}




