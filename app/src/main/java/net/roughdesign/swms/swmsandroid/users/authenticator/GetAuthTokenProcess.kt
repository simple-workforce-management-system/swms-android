package net.roughdesign.swms.swmsandroid.users.authenticator

import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import net.roughdesign.swms.swmsandroid.users.activities.AuthenticateActivity
import net.roughdesign.swms.swmsandroid.users.authtokens.AuthTokenServer
import net.roughdesign.swms.swmsandroid.users.authtokens.AuthTokenServerFactory
import net.roughdesign.swms.swmsandroid.utilities.web.errors.WebErrorHandler
import net.roughdesign.swms.swmsandroid.utilities.web.requests.RequestQueueFactory


class GetAuthTokenProcess(
	private val context: Context,
	private val account: Account,
	private val response: AccountAuthenticatorResponse
) {

	fun start() {
		val accountManager = SwmsAccountAccess.get(context)
		val password = accountManager.getPassword(account)
		if (password == null) respondWithLoginRequest()
		else getTokenFromAccount(accountManager, password)
	}


	private fun respondWithLoginRequest() {
		val intent = AuthenticateActivity.createIntent(context, false)
		intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
		val bundle = Bundle()
		bundle.putParcelable(AccountManager.KEY_INTENT, intent)
		response.onResult(bundle)
	}


	private fun getTokenFromAccount(accountAccess: SwmsAccountAccess, password: String) {
		val authToken = accountAccess.peekAuthToken(account)
		val hasAuthToken = !TextUtils.isEmpty(authToken)
		if (hasAuthToken) respondWithAuthToken(authToken, response)
		else refreshAuthTokenFromServer(password)
	}


	private fun refreshAuthTokenFromServer(password: String) {

		val authTokenServer = AuthTokenServerFactory.create(context)
		val configurableRequest = authTokenServer.createAuthTokenRequest(account.name, password)
		configurableRequest.whenFinished += { sendAccountManagerResponse(it, response, authTokenServer) }
		configurableRequest.whenErrorOccured += { WebErrorHandler.logErrorResponse(context, it) }
		RequestQueueFactory.getOrCreate(context).add(configurableRequest)
	}


	private fun sendAccountManagerResponse(
		it: ByteArray?,
		response: AccountAuthenticatorResponse,
		authTokenServer: AuthTokenServer
	) {
		val jsonWebToken = authTokenServer.parseLoginResponse(it!!)
		val authToken = jsonWebToken.asString
		respondWithAuthToken(authToken, response)
	}


	private fun respondWithAuthToken(authToken: String, response: AccountAuthenticatorResponse) {
		val bundle = Bundle()
		bundle.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
		bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
		bundle.putString(AccountManager.KEY_AUTHTOKEN, authToken)
		response.onResult(bundle)
	}
}