package net.roughdesign.swms.swmsandroid.clients.web

import android.content.Context
import com.android.volley.RequestQueue
import net.roughdesign.swms.swmsandroid.R
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.users.authtokens.JsonWebToken
import net.roughdesign.swms.swmsandroid.utilities.web.repositories.Repository
import net.roughdesign.swms.swmsandroid.utilities.web.requests.RequestQueueFactory
import java.net.URL

object ClientRepositoryFactory {


	fun create(context: Context, authToken: JsonWebToken): Repository<Client> {

		val serverUrl = URL(context.getString(R.string.config__web__server_address))
		val clientBaseUrl = URL(serverUrl, "clients/")
		val clientConverter = ClientJsonConverterFactory.create()
		val requestQueue: RequestQueue = RequestQueueFactory.getOrCreate(context)

		return Repository(
			clientBaseUrl,
			authToken,
			clientConverter,
			requestQueue
		)
	}
}