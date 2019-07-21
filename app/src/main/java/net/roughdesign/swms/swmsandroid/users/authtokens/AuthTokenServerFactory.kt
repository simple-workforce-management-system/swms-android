package net.roughdesign.swms.swmsandroid.users.authtokens

import android.content.Context
import com.squareup.moshi.Moshi
import net.roughdesign.swms.swmsandroid.R
import java.net.URL

object AuthTokenServerFactory {

	fun create(context: Context): AuthTokenServer {
		val baseUrl = URL(context.getString(R.string.config__web__server_address))
		val loginRequestConverter = createLoginRequestConverter()
		return AuthTokenServer(baseUrl, loginRequestConverter)
	}

	private fun createLoginRequestConverter(): LoginRequestConverter {
		val moshi = Moshi.Builder().build()
		val jsonAdapter = moshi.adapter(JsonCredentials::class.java)
		return LoginRequestConverter(jsonAdapter)
	}
}