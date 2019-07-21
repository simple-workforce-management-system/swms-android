package net.roughdesign.swms.swmsandroid.users

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import junit.framework.Assert.assertEquals
import net.roughdesign.swms.swmsandroid.users.authtokens.JsonCredentials
import net.roughdesign.swms.swmsandroid.users.authtokens.JsonWebToken
import net.roughdesign.swms.swmsandroid.users.authtokens.LoginRequestConverter
import org.junit.Test

class LoginRequestConverterTests {


	val tokenText = "token"


	@Test
	fun canParseLoginResponse() {

		val loginRequestConverter = createLoginRequestConverter()
		val byteArray = tokenText.toByteArray()
		val jsonWebToken: JsonWebToken = loginRequestConverter.parseLoginResponse(byteArray)

		assertEquals(tokenText, jsonWebToken.asString)
	}



	private fun createLoginRequestConverter(): LoginRequestConverter {
		val jsonAdapter: JsonAdapter<JsonCredentials> = object : JsonAdapter<JsonCredentials>() {
			override fun toJson(writer: JsonWriter, value: JsonCredentials?) {
				TODO("not implemented")
			}

			override fun fromJson(reader: JsonReader): JsonCredentials? {
				TODO("not implemented")
			}
		}

		return LoginRequestConverter(jsonAdapter)
	}
}