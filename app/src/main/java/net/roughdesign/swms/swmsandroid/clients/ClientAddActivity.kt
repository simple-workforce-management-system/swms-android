package net.roughdesign.swms.swmsandroid.clients

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import net.roughdesign.swms.swmsandroid.R

import kotlinx.android.synthetic.main.client_add_activity.*
import kotlinx.android.synthetic.main.client_add_content.*
import java.net.URL

class ClientAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_add_activity)
        setSupportActionBar(toolbar)

        val nameInput = client_add_name
        val contactDataInput = client_add_contact


        client_add_confirm.setOnClickListener {
            val name = nameInput.text.toString()
            val contactData = contactDataInput.text.toString()




            //val bla = URL("<api call>").

        }
    }

}
