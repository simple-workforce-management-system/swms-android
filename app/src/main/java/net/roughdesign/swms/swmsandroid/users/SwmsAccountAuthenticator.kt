package net.roughdesign.swms.swmsandroid.users

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import net.roughdesign.swms.swmsandroid.R
import android.R.attr.name





class SwmsAccountAuthenticator(val context: Context) : AbstractAccountAuthenticator(context) {

    companion object {
        const val ARG_ACCOUNT_TYPE = "ARG_ACCOUNT_TYPE"
        const val ARG_AUTH_TYPE = "ARG_AUTH_TYPE"
        const val ARG_IS_ADDING_NEW_ACCOUNT = "ARG_IS_ADDING_NEW_ACCOUNT"
    }

    override fun addAccount(
        response: AccountAuthenticatorResponse, accountType: String, authTokenType: String?,
        requiredFeatures: Array<out String>?, options: Bundle?
    ): Bundle {
        Log.i(this::class.java.simpleName, "addAccount")
        val intent = AuthenticateActivity.start(context, true)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }


    override fun confirmCredentials(response: AccountAuthenticatorResponse, account: Account, options: Bundle?)
            : Bundle? {
        Log.i(this::class.java.simpleName, "confirmCredentials")
        val bundle = Bundle()
        bundle.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, true)
        return bundle
    }


    override fun editProperties(response: AccountAuthenticatorResponse?, accountType: String?): Bundle? {
        Log.i(this::class.java.simpleName, "editProperties")
        val intent = Intent()
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }


    override fun getAuthToken(
        response: AccountAuthenticatorResponse, account: Account, authTokenType: String, options: Bundle?
    ): Bundle? {
        Log.i(this::class.java.simpleName, "getAuthToken")

        val accountManager = AccountManager.get(context)
        var authToken = accountManager.peekAuthToken(account, authTokenType)
        val hasAuthToken = !TextUtils.isEmpty(authToken)
        if(hasAuthToken){
            val result = Bundle()
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken)
            return result
        }


        val username = account.name
        val password = accountManager.getPassword(account)
        if(password!=null) {
            authToken = SwmsLogin.getAuthToken(username, password)
            if(!TextUtils.isEmpty(authToken)){
                val result = Bundle()
                result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
                result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
                result.putString(AccountManager.KEY_AUTHTOKEN, authToken)
                return result
            }

        }

        val intent = AuthenticateActivity.start(context, false)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }


    override fun getAuthTokenLabel(authTokenType: String): String {
        Log.i(this::class.java.simpleName, "getAuthTokenLabel")
        return authTokenType
    }


    override fun hasFeatures(response: AccountAuthenticatorResponse, account: Account, features: Array<out String>)
            : Bundle? {
        Log.i(this::class.java.simpleName, "hasFeatures")
        val hasFeatures = features.isEmpty()
        val bundle = Bundle()
        bundle.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, hasFeatures)
        return bundle
    }


    override fun updateCredentials(
        response: AccountAuthenticatorResponse, account: Account, authTokenType: String?, options: Bundle?
    ): Bundle? {
        Log.i(this::class.java.simpleName, "updateCredentials")
        val bundle = Bundle()
        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
        bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, context.getString(R.string.accounts__account_type))
        return bundle
    }
}