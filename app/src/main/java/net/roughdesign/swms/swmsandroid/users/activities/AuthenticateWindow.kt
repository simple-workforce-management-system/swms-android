package net.roughdesign.swms.swmsandroid.users.activities

import com.android.volley.VolleyError

interface AuthenticateWindow{
	fun showVolleyError(error: VolleyError)
	fun closeWithSuccess()

}