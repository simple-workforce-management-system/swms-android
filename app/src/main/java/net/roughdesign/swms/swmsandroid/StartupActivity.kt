package net.roughdesign.swms.swmsandroid

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity


class StartupActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.startup__activity)


		Handler().postDelayed({
			continueToDashboard()
		}, 1000)
	}


	override fun onBackPressed() {
	}


	private fun continueToDashboard() {
		DashboardActivity.start(this)
		overridePendingTransition(R.anim.in_scale, R.anim.out_alpha)
		finish()
	}
}
