package net.roughdesign.swms.swmsandroid.users.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.VolleyError
import kotlinx.android.synthetic.main.authenticate__activity.*
import kotlinx.android.synthetic.main.authenticate__content.*
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.utilities.web.errors.WebErrorHandler
import org.jetbrains.anko.contentView


class AuthenticateActivity : AppCompatAccountAuthenticatorActivity(), AuthenticateWindow {

	private lateinit var authenticatePresenter: AuthenticatePresenter


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.authenticate__activity)


		val createNew = intent.getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)
		authenticatePresenter = AuthenticatePresenterFactory.create(this, this, createNew)

		fab.setOnClickListener { submit() }
	}


	private fun submit() {

		val username = name_input.text.toString()
		val password = password_input.text.toString()
		authenticatePresenter.submit(username, password)
	}


	override fun closeWithSuccess() {
		setAccountAuthenticatorResult(intent.extras)
		setResult(Activity.RESULT_OK, intent)
		finish()
	}


	override fun showVolleyError(error: VolleyError) {
		WebErrorHandler.showFeedbackOnScreen(contentView!!, error)
	}


	companion object {
		const val ARG_IS_ADDING_NEW_ACCOUNT: String = "ARG_IS_ADDING_NEW_ACCOUNT"

		fun createIntent(context: Context, createNew: Boolean): Intent {
			val intent = Intent(context, AuthenticateActivity::class.java)
			intent.putExtra(ARG_IS_ADDING_NEW_ACCOUNT, createNew)
			return intent
		}
	}
}


