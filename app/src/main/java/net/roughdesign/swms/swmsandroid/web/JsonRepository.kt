package net.roughdesign.swms.swmsandroid.web

import com.android.volley.Request
import com.google.gson.Gson
import java.net.URL

class JsonRepository<T>(
    private val jsonRequestFactory: JsonRequestFactory,
    private val urlSet: UrlSet
) {


    fun create(model: T, responseReacter: ResponseReacter<T>) {
        val apiAccess = urlSet.create
        val requestBody = Gson().toJson(model)
        jsonRequestFactory.sendRequest(apiAccess, requestBody, responseReacter)
    }

    fun update(model: T, responseReacter: ResponseReacter<T>) {
        val apiAccess = urlSet.update
        val requestBody = Gson().toJson(model)
        jsonRequestFactory.sendRequest(apiAccess, requestBody, responseReacter)
    }

    fun delete(model: T, responseReacter: ResponseReacter<Boolean>) {
        val apiAccess = urlSet.delete
        val requestBody = Gson().toJson(model)
        jsonRequestFactory.sendRequest(apiAccess, requestBody, responseReacter)
    }

    fun index(responseReacter: ResponseReacter<List<T>>) {
        val apiAccess = urlSet.index
        jsonRequestFactory.sendRequest(apiAccess, null, responseReacter)
    }

    fun get(id: Long, responseReacter: ResponseReacter<T>) {
        val apiAccessWithParameter = urlSet.get
        val apiAccessWithId = apiAccessWithParameter.getFullUrl(id.toString())
        jsonRequestFactory.sendRequest(apiAccessWithId, null, responseReacter)
    }
}

fun createDefaultUrlSet(baseUrl: URL): UrlSet {
    val create = ApiAccess(Request.Method.POST, baseUrl)
    val update = ApiAccess(Request.Method.PUT, baseUrl)
    val delete = ApiAccess(Request.Method.DELETE, baseUrl)
    val index = ApiAccess(Request.Method.GET, baseUrl)
    val get = ApiAccessWithParameter(Request.Method.GET, baseUrl)
    return UrlSet(create, update, delete, index, get)
}

data class UrlSet(
    val create: ApiAccess,
    val update: ApiAccess,
    val delete: ApiAccess,
    val index: ApiAccess,
    val get: ApiAccessWithParameter
)

data class ApiAccess(val method: Int, val url: URL)

class ApiAccessWithParameter(private val method: Int, private val url: URL){
    fun getFullUrl( parameter : String) : ApiAccess{
        val fullUrl = URL(url, parameter)
        return ApiAccess(method, fullUrl)
    }
}
