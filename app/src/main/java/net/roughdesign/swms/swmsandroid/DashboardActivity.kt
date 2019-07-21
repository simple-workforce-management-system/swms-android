package net.roughdesign.swms.swmsandroid

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.dashboard__content.*
import net.roughdesign.swms.swmsandroid.clients.activities.list.ClientListActivity

class DashboardActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.dashboard__activty)
	}

	override fun onResume() {
		super.onResume()

		clients_button.setOnClickListener {
			val intent = Intent(this, ClientListActivity::class.java)
			startActivity(intent)
		}
	}
}
