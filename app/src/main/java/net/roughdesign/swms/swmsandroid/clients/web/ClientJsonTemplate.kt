package net.roughdesign.swms.swmsandroid.clients.web

import com.squareup.moshi.Json

class ClientJsonTemplate {

	@Json(name = "id")
	var id: Long? = null

	@Json(name = "name")
	var name: String? = null

	@Json(name = "contactData")
	var contactData: String? = null
}

