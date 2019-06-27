package net.roughdesign.swms.swmsandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.os.Handler


class StartupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startup__activity)


        Handler().postDelayed({
            DashboardActivity.start(this)
            overridePendingTransition(R.anim.in_scale, R.anim.out_alpha)
            finish()
        }, 2000)
    }


    override fun onBackPressed() {
    }
}
