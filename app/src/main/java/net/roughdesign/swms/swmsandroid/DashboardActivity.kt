package net.roughdesign.swms.swmsandroid

import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.dashboard__content.*
import net.roughdesign.swms.swmsandroid.clients.activities.list.ClientListActivity
import net.roughdesign.swms.swmsandroid.users.AuthenticateActivity

class DashboardActivity : AppCompatActivity() {

	companion object {
		const val PICK_CONTACT_REQUEST = 1

		fun start(context: Context) {
			val intent = Intent(context, DashboardActivity::class.java)
			context.startActivity(intent)
		}
	}


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.dashboard__activty)
	}

	override fun onResume() {
		super.onResume()

		val accountManager = AccountManager.get(this)
		val accountType = getString(R.string.accounts__account_type)
		val accounts = accountManager.getAccountsByType(accountType)

		if (accounts.isEmpty()) {

			accountManager.addAccount(accountType, null, null, null, this, null, null)
			return
		}

		val account = accounts[0]
		clients_button.setOnClickListener {
			ClientListActivity.start(this, account)
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		if (requestCode != PICK_CONTACT_REQUEST) return
		if (resultCode != RESULT_OK) return

		Log.w(DashboardActivity::class.java.toString(), "AAAA onActivityResult 1")
		val account = AuthenticateActivity.getResult(data!!)
		clients_button.setOnClickListener {
			ClientListActivity.start(this, account)
		}
	}
}
