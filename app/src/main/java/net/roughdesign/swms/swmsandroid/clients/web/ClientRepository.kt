package net.roughdesign.swms.swmsandroid.clients.web

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.utilities.json.JsonConverter
import net.roughdesign.swms.swmsandroid.utilities.web.configurablerequests.ConfigurableRequest
import net.roughdesign.swms.swmsandroid.utilities.web.configurablerequests.setAsAuthorizedJson
import java.net.URL


class ClientRepository(
	private val baseUrl: URL,
	private val authToken: String,
	private val clientConverter: JsonConverter<Client>,
	private val requestQueue: RequestQueue
) {


	fun create(client: Client, listener: Response.Listener<Client>, errorListener: Response.ErrorListener?) {

		val method = Request.Method.POST
		val url = URL(baseUrl, "clients/createRepoModelConverter/")
		val requestBody = clientConverter.encode(client)

		val configurableRequest =
			ConfigurableRequest(
				method,
				url,
				errorListener
			)
		configurableRequest.setAsAuthorizedJson(authToken)
		configurableRequest.setRequestBody(requestBody)
		configurableRequest.whenFinished += { listener.onResponse(clientConverter.convert(it)) }
		requestQueue.add(configurableRequest)
	}


	fun save(client: Client, listener: Response.Listener<Client>, errorListener: Response.ErrorListener?) {
		val method = Request.Method.PUT
		val url = URL(baseUrl, "clients/update/")

		val configurableRequest =
			ConfigurableRequest(
				method,
				url,
				errorListener
			)
		configurableRequest.setAsAuthorizedJson(authToken)
		val requestBody = clientConverter.encode(client)
		configurableRequest.setRequestBody(requestBody)
		configurableRequest.whenFinished += { listener.onResponse(clientConverter.convert(it)) }
		requestQueue.add(configurableRequest)
	}


	fun delete(client: Client, listener: Response.Listener<Boolean>, errorListener: Response.ErrorListener?) {
		val method = Request.Method.DELETE
		val url = URL(baseUrl, "clients/delete/${client.id}")

		val configurableRequest =
			ConfigurableRequest(
				method,
				url,
				errorListener
			)
		configurableRequest.setAsAuthorizedJson(authToken)
		configurableRequest.whenFinished += { listener.onResponse(it != null) }
		requestQueue.add(configurableRequest)
	}


	fun index(listener: Response.Listener<List<Client>>, errorListener: Response.ErrorListener?) {

		val method = Request.Method.GET
		val url = URL(baseUrl, "clients/")

		val configurableRequest =
			ConfigurableRequest(
				method,
				url,
				errorListener
			)
		configurableRequest.setAsAuthorizedJson(authToken)
		configurableRequest.whenFinished += { listener.onResponse(clientConverter.convertList(it)) }
		requestQueue.add(configurableRequest)
	}


	fun get(id: Long, listener: Response.Listener<Client>, errorListener: Response.ErrorListener?) {
		val method = Request.Method.GET
		val url = URL(baseUrl, "clients/get/$id")

		val configurableRequest =
			ConfigurableRequest(
				method,
				url,
				errorListener
			)
		configurableRequest.setAsAuthorizedJson(authToken)
		configurableRequest.whenFinished += { listener.onResponse(clientConverter.convert(it)) }
		requestQueue.add(configurableRequest)
	}
}




