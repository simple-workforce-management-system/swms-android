package net.roughdesign.swms.swmsandroid.clients.web

import com.android.volley.Request
import com.android.volley.RequestQueue
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.web.ServerConfiguration
import net.roughdesign.swms.swmsandroid.web.repositories.StringResponseReacter
import java.net.URL

class ClientRepository(
	private val _serverConfiguration: ServerConfiguration,
	private val _jsonConverter: JsonConverter<Client>,
	private val _jsonRequestFactory: JsonRequestFactory,
	private val _requestQueue: RequestQueue
) {


	fun create(client: Client, responseReacter: StringResponseReacter) {

		val method = Request.Method.POST
		val url = URL(_serverConfiguration.url, "clients/create/")
		val requestBody = _jsonConverter.toJson(client)
		val request = _jsonRequestFactory.createRequest(method, url, requestBody, responseReacter)
		_requestQueue.add(request)
	}


	fun save(client: Client, responseReacter: StringResponseReacter) {
		val method = Request.Method.PUT
		val url = URL(_serverConfiguration.url, "clients/update/")
		val requestBody = _jsonConverter.toJson(client)
		val request = _jsonRequestFactory.createRequest(method, url, requestBody, responseReacter)
		_requestQueue.add(request)
	}


	fun delete(client: Client, responseReacter: StringResponseReacter) {
		val method = Request.Method.DELETE
		val url = URL(_serverConfiguration.url, "clients/delete/${client.id}")
		val requestBody = null
		val request = _jsonRequestFactory.createRequest(method, url, requestBody, responseReacter)
		_requestQueue.add(request)
	}


	fun index(responseReacter: StringResponseReacter) {
		val method = Request.Method.GET
		val url = URL(_serverConfiguration.url, "clients/")
		val requestBody = null
		val request = _jsonRequestFactory.createRequest(method, url, requestBody, responseReacter)
		_requestQueue.add(request)
	}


	fun get(id: Long, responseReacter: StringResponseReacter) {
		val method = Request.Method.GET
		val url = URL(_serverConfiguration.url, "clients/get/$id")
		val requestBody = null
		val request = _jsonRequestFactory.createRequest(method, url, requestBody, responseReacter)
		_requestQueue.add(request)
	}
}


