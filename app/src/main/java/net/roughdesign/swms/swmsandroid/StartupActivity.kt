package net.roughdesign.swms.swmsandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class StartupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startup_activity)

        val intent =  Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.startup_out, R.anim.dashboard_in)
    }
}
