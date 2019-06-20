package net.roughdesign.swms.swmsandroid

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.dashboard_activty.*
import kotlinx.android.synthetic.main.dashboard_content.*
import net.roughdesign.swms.swmsandroid.clients.ClientDetailActivity
import net.roughdesign.swms.swmsandroid.clients.ClientListActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activty)
        setSupportActionBar(toolbar)

        dashboard_clients_button.setOnClickListener {
            val intent = Intent(this, ClientListActivity::class.java)
            startActivity(intent)
        }

    }

}
