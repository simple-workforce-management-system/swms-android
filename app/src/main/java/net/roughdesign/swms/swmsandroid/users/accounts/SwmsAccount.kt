package net.roughdesign.swms.swmsandroid.users.accounts

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.app.Activity
import android.os.Bundle

class SwmsAccount(
	private val accountManager: AccountManager,
	private val accountType: String,
	private val authTokenType: String,
	private val account: Account
) {


	fun getAuthToken(activity: Activity, callback: AccountManagerCallback<Bundle>?): AccountManagerFuture<Bundle>? {
		return accountManager.getAuthToken(account, authTokenType, null, activity, callback, null)
	}
}



