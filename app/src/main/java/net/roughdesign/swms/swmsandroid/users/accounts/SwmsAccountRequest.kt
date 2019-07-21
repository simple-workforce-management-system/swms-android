package net.roughdesign.swms.swmsandroid.users.accounts

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.app.Activity
import android.os.Bundle
import net.roughdesign.swms.swmsandroid.utilities.events.EventI

class SwmsAccountRequest(
	private val activity: Activity,
	private val accountManager: AccountManager,
	private val accountType: String,
	private val authTokenType: String
) {


	val whenDone = EventI<SwmsAccount>()


	fun start() {
		val accounts = accountManager.getAccountsByType(accountType)
		if (accounts.isNotEmpty()) finishWithSuccess(accounts[0])
		else requestAccountCreationFirst()
	}


	private fun requestAccountCreationFirst() {
		val accountCallback = AccountManagerCallback<Bundle> { finishFromBundle(it) }
		accountManager.addAccount(accountType, authTokenType, null, null, activity, accountCallback, null)
	}


	private fun finishFromBundle(accountManagerFuture: AccountManagerFuture<Bundle>) {
		val bundle = accountManagerFuture.result
		val accountName = bundle.getString(AccountManager.KEY_ACCOUNT_NAME)
		val account = Account(accountName, accountType)
		finishWithSuccess(account)
	}


	private fun finishWithSuccess(account: Account) {
		val swmsAccountManager = SwmsAccount(
			accountManager,
			accountType,
			authTokenType,
			account
		)
		whenDone.invoke(swmsAccountManager)
	}
}


