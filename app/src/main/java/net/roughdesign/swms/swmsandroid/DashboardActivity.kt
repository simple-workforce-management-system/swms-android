package net.roughdesign.swms.swmsandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.dashboard__content.*
import net.roughdesign.swms.swmsandroid.clients.ClientListActivity

class DashboardActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, DashboardActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard__activty)

        clients_button.setOnClickListener {
            ClientListActivity.start(this)
        }
    }
}
