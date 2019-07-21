package net.roughdesign.swms.swmsandroid.users.authtokens

import com.squareup.moshi.JsonAdapter

class LoginRequestConverter(private val jsonAdapter: JsonAdapter<JsonCredentials>) {


	fun createLoginJson(userName: String, password: String): ByteArray? {
		val credentials = JsonCredentials(userName, password)
		val toJson = jsonAdapter.toJson(credentials)
		return toJson.toByteArray()
	}


	fun parseLoginResponse(byteArray: ByteArray): JsonWebToken {
		val asString = String(byteArray)
		return JsonWebToken(asString)
	}
}
