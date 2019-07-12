package net.roughdesign.swms.swmsandroid.users

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.VolleyError
import com.google.gson.Gson
import kotlinx.android.synthetic.main.authenticate__activity.*
import kotlinx.android.synthetic.main.authenticate__content.*
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.users.models.User
import net.roughdesign.swms.swmsandroid.utilities.AppCompatAccountAuthenticatorActivity
import net.roughdesign.swms.swmsandroid.web.WebErrorHandler
import net.roughdesign.swms.swmsandroid.web.repositories.ResponseReacter
import org.jetbrains.anko.contentView


class AuthenticateActivity : AppCompatAccountAuthenticatorActivity() {

	companion object {
		const val ARG_IS_ADDING_NEW_ACCOUNT: String = "ARG_IS_ADDING_NEW_ACCOUNT"
		const val ARG_RESULT: String = "ARG_RESULT"


		fun start(context: Context, createNew: Boolean): Intent {
			val intent = Intent(context, AuthenticateActivity::class.java)
			intent.putExtra(ARG_IS_ADDING_NEW_ACCOUNT, createNew)
			return intent
		}

		fun getResult(intent: Intent): Account {
			return intent.getParcelableExtra(ARG_RESULT)
		}
	}


	private lateinit var accountManager: AccountManager
	private lateinit var accountType: String
	private lateinit var authTokenType: String

	private var createNew: Boolean = false


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.authenticate__activity)

		accountManager = AccountManager.get(this)
		accountType = getString(R.string.accounts__account_type)
		authTokenType = getString(R.string.accounts__auth_token_type)
		createNew = intent.getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)


		fab.setOnClickListener { view ->
			submit()
		}
	}


	private fun submit() {

		val username = name_input.text.toString()
		val password = password_input.text.toString()


		SwmsLogin.login(this, username, password, object : ResponseReacter() {
			override fun onResponse(response: ByteArray) {

				val asString = String(response)
				val user = Gson().fromJson(asString, User::class.java)
				val authToken = user.token

				finishUpdatingAccount(username, password, authToken)
			}

			override fun onErrorResponse(error: VolleyError) {
				WebErrorHandler.showFeedbackOnScreen(contentView!!, error)
			}
		})
	}


	private fun finishUpdatingAccount(username: String, password: String, authToken: String) {
		val account = Account(username, accountType)
		if (createNew) createAndroidAccount(account, password, authToken)
		else accountManager.setPassword(account, password)
		finishActivitySuccessfully()
	}


	private fun createAndroidAccount(account: Account, password: String, authToken: String) {
		accountManager.addAccountExplicitly(account, password, null)
		accountManager.setAuthToken(account, authTokenType, authToken)
	}


	private fun finishActivitySuccessfully() {
		setAccountAuthenticatorResult(intent.extras)
		setResult(Activity.RESULT_OK, intent)
		finish()
	}
}
