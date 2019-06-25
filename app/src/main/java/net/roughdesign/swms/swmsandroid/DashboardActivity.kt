package net.roughdesign.swms.swmsandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.dashboard_activty.*
import kotlinx.android.synthetic.main.dashboard_content.*
import net.roughdesign.swms.swmsandroid.clients.ClientDetailActivity
import net.roughdesign.swms.swmsandroid.clients.ClientListActivity
import net.roughdesign.swms.swmsandroid.clients.models.Client

class DashboardActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, DashboardActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activty)

        dashboard_clients_button.setOnClickListener {
            ClientListActivity.start(this)
        }
    }
}
