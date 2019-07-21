package net.roughdesign.swms.swmsandroid.users.activities

import com.android.volley.RequestQueue
import net.roughdesign.swms.swmsandroid.users.authenticator.SwmsAccountAccess
import net.roughdesign.swms.swmsandroid.users.authtokens.AuthTokenServer

class AuthenticatePresenter(
	private val authenticateWindow: AuthenticateWindow,
	private val authTokenServer: AuthTokenServer,
	private val createNew: Boolean,
	private val swmsAccountAccess: SwmsAccountAccess,
	private val requestQueue: RequestQueue

) {


	 fun submit(userName: String, password: String) {

		 val configurableRequest = authTokenServer.createAuthTokenRequest(userName, password)
		 configurableRequest.whenFinished += { reactToAuthTokenResponse(userName, password, it)}
		 configurableRequest.whenErrorOccured+={ authenticateWindow.showVolleyError(it)}
		 requestQueue.add(configurableRequest)
	}


	private fun reactToAuthTokenResponse(username: String, password: String, byteArray: ByteArray?) {
		val authToken = authTokenServer.parseLoginResponse(byteArray!!)
		if (createNew) swmsAccountAccess.addAccountExplicitly(username, password, authToken)
		else swmsAccountAccess.setPassword(username, password)
		authenticateWindow.closeWithSuccess()
	}
}