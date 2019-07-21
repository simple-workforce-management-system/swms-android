package net.roughdesign.swms.swmsandroid.users.activities

import android.content.Context

interface IAuthenticatePresenterFactory{
	fun create(context: Context, window: AuthenticateWindow, createNew:Boolean): AuthenticatePresenter
}