package net.roughdesign.swms.swmsandroid.web.urlsets

import java.net.URL

class ApiAccessWithParameter(private val method: Int, private val url: URL){
    fun getFullUrl( parameter : String) : ApiAccess {
        val fullUrl = URL(url, parameter)
        return ApiAccess(method, fullUrl)
    }
}