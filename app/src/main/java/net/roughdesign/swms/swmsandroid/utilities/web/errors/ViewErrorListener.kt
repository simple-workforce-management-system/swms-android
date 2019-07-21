package net.roughdesign.swms.swmsandroid.utilities.web.errors

import android.view.View
import com.android.volley.Response
import com.android.volley.VolleyError

class ViewErrorListener(private val view: View) : Response.ErrorListener {

	override fun onErrorResponse(error: VolleyError) {
		WebErrorHandler.showFeedbackOnScreen(view, error)
	}
}


