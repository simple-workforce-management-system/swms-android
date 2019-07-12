package net.roughdesign.swms.swmsandroid.web.urlsets

import com.android.volley.Request
import java.net.URL

data class UrlSet(
	val create: ApiAccess,
	val update: ApiAccess,
	val delete: ApiAccessWithParameter,
	val index: ApiAccess,
	val get: ApiAccessWithParameter
) {
	companion object {
		fun createDefaultUrlSet(baseUrl: URL): UrlSet {
			val create = ApiAccess(Request.Method.POST, baseUrl)
			val update = ApiAccess(Request.Method.PUT, baseUrl)
			val delete = ApiAccessWithParameter(Request.Method.DELETE, baseUrl)
			val index = ApiAccess(Request.Method.GET, baseUrl)
			val get = ApiAccessWithParameter(Request.Method.GET, baseUrl)
			return UrlSet(create, update, delete, index, get)
		}
	}
}

