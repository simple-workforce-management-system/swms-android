package net.roughdesign.swms.swmsandroid.users.authtokens

import com.squareup.moshi.Json

class JsonCredentials(
	@Json(name = "userName")
	val userName: String,

	@Json(name = "password")
	val password: String? = null
)

