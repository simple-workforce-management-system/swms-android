package net.roughdesign.swms.swmsandroid.clients.models

import android.content.Context
import net.roughdesign.swms.swmsandroid.web.JsonRepository
import net.roughdesign.swms.swmsandroid.web.SwmsRequestQueue
import net.roughdesign.swms.swmsandroid.web.urlsets.UrlSet
import java.io.Serializable
import java.net.URL

data class Client(val id: Long, val name: String, val contactData: String) : Serializable {

    companion object {

        @Volatile
        private var INSTANCE: JsonRepository<Client>? = null


        fun getRepository(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: createRepository(context).also { INSTANCE = it }
            }


        private fun createRepository(context: Context): JsonRepository<Client> {
            val urlSet = UrlSet.createDefaultUrlSet(URL("http://swmsapi.azurewebsites.net/clients/"))
            val requestQueue = SwmsRequestQueue.getRequestQueue(context)
            return JsonRepository(urlSet, requestQueue)
        }
    }
}