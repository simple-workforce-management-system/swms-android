package net.roughdesign.swms.swmsandroid.users.authenticator

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.users.authtokens.JsonWebToken

class SwmsAccountAccess(
	private val accountManager: AccountManager,
	private val accountType: String,
	private val authTokenType: String
) {

	fun addAccountExplicitly(userName: String, password: String, authToken: JsonWebToken) {
		val account = Account(userName, accountType)
		accountManager.addAccountExplicitly(account, password, null)
		accountManager.setAuthToken(account, authTokenType, authToken.asString)
	}


	fun peekAuthToken(account: Account): String {
		return accountManager.peekAuthToken(account, authTokenType)
	}


	fun setPassword(userName: String, password: String) {
		val account = Account(userName, accountType)
		accountManager.setPassword(account, password)
	}


	fun getPassword(account: Account): String? {
		return accountManager.getPassword(account)
	}


	companion object {
		fun get(context: Context): SwmsAccountAccess {
			val accountManager = AccountManager.get(context)
			val accountType = context.getString(R.string.accounts__account_type)
			val authTokenType = context.getString(R.string.accounts__auth_token_type)
			return SwmsAccountAccess(accountManager, accountType, authTokenType)
		}
	}
}