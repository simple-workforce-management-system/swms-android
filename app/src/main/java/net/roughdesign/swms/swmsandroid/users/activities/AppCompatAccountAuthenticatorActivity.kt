package net.roughdesign.swms.swmsandroid.users.activities

import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


/**
 * This is 1:1 android's own AccountAuthenticatorActivity, just derived from an AppCompatActivity
 * instead of a normal Activity
 */
abstract class AppCompatAccountAuthenticatorActivity : AppCompatActivity() {

	private var accountAuthenticatorResponse: AccountAuthenticatorResponse? = null
	private var resultBundle: Bundle? = null
	/**
	 * Set the result that is to be sent as the result of the request that caused this
	 * Activity to be launched. If result is null or this method is never called then
	 * the request will be canceled.
	 * @param result this is returned as the result of the AbstractAccountAuthenticator request
	 */
	fun setAccountAuthenticatorResult(result: Bundle?) {
		resultBundle = result
	}

	/**
	 * Retrieves the AccountAuthenticatorResponse from either the intent of the icicle, if the
	 * icicle is non-zero.
	 * @param icicle the save instance data of this Activity, may be null
	 */
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		accountAuthenticatorResponse = intent.getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE)
		if (accountAuthenticatorResponse != null) {
			accountAuthenticatorResponse!!.onRequestContinued()
		}
	}

	/**
	 * Sends the result or a Constants.ERROR_CODE_CANCELED error if a result isn't present.
	 */
	override fun finish() {
		if (accountAuthenticatorResponse != null) {
			// send the result bundle back if set, otherwise send an error.
			if (resultBundle != null) {
				accountAuthenticatorResponse!!.onResult(resultBundle)
			} else {
				accountAuthenticatorResponse!!.onError(
					AccountManager.ERROR_CODE_CANCELED,
					"canceled"
				)
			}
			accountAuthenticatorResponse = null
		}
		super.finish()
	}
}