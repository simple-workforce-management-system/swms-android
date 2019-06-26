package net.roughdesign.swms.swmsandroid.web.repositories

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class SwmsRequestQueue constructor(context: Context) {


    companion object {
        @Volatile
        private var INSTANCE: SwmsRequestQueue? = null

        fun getRequestQueue(context: Context): RequestQueue {
            return getInstance(context).requestQueue
        }


        private fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: SwmsRequestQueue(context.applicationContext).also {
                    INSTANCE = it
                }
            }
    }


    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
}
