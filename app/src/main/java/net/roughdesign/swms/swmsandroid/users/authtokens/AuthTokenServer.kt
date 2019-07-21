package net.roughdesign.swms.swmsandroid.users.authtokens

import com.android.volley.Request
import com.android.volley.Response
import net.roughdesign.swms.swmsandroid.utilities.web.requests.ConfigurableRequest
import net.roughdesign.swms.swmsandroid.utilities.web.requests.setAsJsonRequest
import net.roughdesign.swms.swmsandroid.utilities.web.requests.setRetryPolicy
import java.net.URL

class AuthTokenServer(
	private val baseUrl: URL,
	private val loginRequestConverter: LoginRequestConverter
) {


	fun createAuthTokenRequest(userName: String, password: String): ConfigurableRequest {

		val requestBody = loginRequestConverter.createLoginJson(userName, password)
		val configurableRequest = createConfigurableRequest(null)
		configureRequest(configurableRequest, requestBody)
		return configurableRequest
	}


	private fun createConfigurableRequest(errorListener: Response.ErrorListener?): ConfigurableRequest {
		val method = Request.Method.POST
		val url = URL(baseUrl, "users/sign-in")
		return ConfigurableRequest(method, url, errorListener)
	}


	private fun configureRequest(configurableRequest: ConfigurableRequest, requestBody: ByteArray?) {
		configurableRequest.setAsJsonRequest()
		configurableRequest.setRetryPolicy(3000, 2)
		configurableRequest.setRequestBody(requestBody)
	}


	fun parseLoginResponse(byteArray: ByteArray): JsonWebToken {
		return loginRequestConverter.parseLoginResponse(byteArray)
	}
}


