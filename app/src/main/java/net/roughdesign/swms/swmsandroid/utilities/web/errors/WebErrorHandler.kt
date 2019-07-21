package net.roughdesign.swms.swmsandroid.utilities.web.errors

import android.content.Context
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.android.volley.*
import net.roughdesign.swms.swmsandroid.R


object WebErrorHandler {

	fun showFeedbackOnScreen(view: View, error: VolleyError) {

		Log.e(this::class.java.simpleName, error.toString() + ": " + error.message.toString())
		val feedbackText = getFeedbackText(error)
		Snackbar.make(view, feedbackText, Snackbar.LENGTH_LONG).show()
	}


	fun logErrorResponse(context: Context, error: VolleyError) {

		val feedbackTextId =
			getFeedbackText(error)
		val feedbackText = context.getString(feedbackTextId)
		Log.e(this::class.java.simpleName, feedbackText)
	}


	private fun getFeedbackText(error: VolleyError): Int {
		return when (error) {
			is NoConnectionError -> R.string.error_network__no_connection
			is TimeoutError -> R.string.error_network__timeout
			is AuthFailureError -> R.string.error_network__auth
			is ServerError -> R.string.error_network__server
			is NetworkError -> R.string.error_network
			is ParseError -> R.string.error_network__parse
			else -> throw IllegalStateException()
		}
	}
}