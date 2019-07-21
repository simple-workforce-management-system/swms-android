package net.roughdesign.swms.swmsandroid.users.activities

import android.content.Context
import net.roughdesign.swms.swmsandroid.users.authenticator.SwmsAccountAccess
import net.roughdesign.swms.swmsandroid.users.authtokens.AuthTokenServerFactory
import net.roughdesign.swms.swmsandroid.utilities.web.requests.RequestQueueFactory

object AuthenticatePresenterFactory : IAuthenticatePresenterFactory {

	override fun create(context: Context, window: AuthenticateWindow, createNew: Boolean): AuthenticatePresenter {

		val authTokenServer = AuthTokenServerFactory.create(context)
		val swmsAccountManager = SwmsAccountAccess.get(context)
		val requestQueue = RequestQueueFactory.getOrCreate(context)

		return AuthenticatePresenter(window, authTokenServer, createNew, swmsAccountManager, requestQueue)
	}
}