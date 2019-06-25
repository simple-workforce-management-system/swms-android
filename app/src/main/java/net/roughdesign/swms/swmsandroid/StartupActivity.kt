package net.roughdesign.swms.swmsandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class StartupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startup_activity)


        DashboardActivity.start(this)
        overridePendingTransition(R.anim.startup_out, R.anim.dashboard_in)

        finish()
    }
}
