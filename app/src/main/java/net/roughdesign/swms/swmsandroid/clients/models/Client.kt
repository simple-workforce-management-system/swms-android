package net.roughdesign.swms.swmsandroid.clients.models

import android.content.Context
import com.android.volley.toolbox.Volley
import net.roughdesign.swms.swmsandroid.web.JsonRepository
import net.roughdesign.swms.swmsandroid.web.urlsets.UrlSet
import java.io.Serializable
import java.net.URL

data class Client(val id: Long, val name: String, val contactData: String) : Serializable {

    companion object {
        fun createRepository(context: Context): JsonRepository<Client> {
            val queue = Volley.newRequestQueue(context)
            val urlSet = UrlSet.createDefaultUrlSet(URL("http://swmsapi.azurewebsites.net/clients"))
            return JsonRepository(urlSet, queue)
        }
    }
}