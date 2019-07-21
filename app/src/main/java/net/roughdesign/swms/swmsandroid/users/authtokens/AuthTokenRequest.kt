package net.roughdesign.swms.swmsandroid.users.authtokens

import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.app.Activity
import android.os.Bundle
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.users.accounts.SwmsAccount
import net.roughdesign.swms.swmsandroid.users.accounts.SwmsAccountRequest
import net.roughdesign.swms.swmsandroid.utilities.events.EventI

class AuthTokenRequest(private val activity: Activity) {

	val whenRequestFinished = EventI<JsonWebToken>()


	fun start() {
		val swmsAccountRequest = createAccountRequest()
		swmsAccountRequest.whenDone += { requestAuthToken(activity, it) }
		swmsAccountRequest.start()
	}


	private fun createAccountRequest(): SwmsAccountRequest {
		val accountManager = AccountManager.get(activity)
		val accountType = activity.getString(R.string.accounts__account_type)
		val authTokenType = activity.getString(R.string.accounts__auth_token_type)
		return SwmsAccountRequest(
			activity,
			accountManager,
			accountType,
			authTokenType
		)
	}


	private fun requestAuthToken(activity: Activity, value: SwmsAccount) {
		val authTokenListener = AccountManagerCallback<Bundle> { reactToAuthToken(it) }
		value.getAuthToken(activity, authTokenListener)
	}


	private fun reactToAuthToken(it: AccountManagerFuture<Bundle>) {
		val bundle = it.result
		val authTokenString = bundle.getString(AccountManager.KEY_AUTHTOKEN)
		val jsonWebToken = JsonWebToken(authTokenString)
		whenRequestFinished.invoke(jsonWebToken)
	}
}