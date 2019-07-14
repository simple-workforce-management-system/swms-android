package net.roughdesign.swms.swmsandroid.utilities.web

import android.view.View
import com.android.volley.Response
import com.android.volley.VolleyError

class DefaultErrorListener(private val view: View) : Response.ErrorListener {

	override fun onErrorResponse(error: VolleyError) {
		WebErrorHandler.showFeedbackOnScreen(view, error)
	}
}