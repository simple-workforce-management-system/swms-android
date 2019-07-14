package net.roughdesign.swms.swmsandroid.clients.web

import android.content.Context
import com.android.volley.RequestQueue
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.utilities.web.repositories.SwmsRequestQueue
import java.net.URL

class ClientRepositoryFactory(private val clientJsonConverterFactory: ClientJsonConverterFactory) {


	fun create(context: Context, authToken: String): ClientRepository {

		val baseUrl = URL(context.getString(R.string.config__web__server_address))
		val clientConverter = clientJsonConverterFactory.createRepoModelConverter()
		val requestQueue: RequestQueue = SwmsRequestQueue.getRequestQueue(context)

		return ClientRepository(baseUrl, authToken, clientConverter, requestQueue)
	}
}