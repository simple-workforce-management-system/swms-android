package net.roughdesign.swms.swmsandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.os.Handler


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
