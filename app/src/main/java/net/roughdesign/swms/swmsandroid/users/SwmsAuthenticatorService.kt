package net.roughdesign.swms.swmsandroid.users

import android.app.Service
import android.content.Intent
import android.os.IBinder

class SwmsAuthenticatorService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        val authenticator = SwmsAccountAuthenticator(this)
        return authenticator.iBinder
    }
}