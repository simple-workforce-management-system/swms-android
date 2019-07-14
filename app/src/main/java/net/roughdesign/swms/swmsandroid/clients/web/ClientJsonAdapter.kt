package net.roughdesign.swms.swmsandroid.clients.web

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import net.roughdesign.swms.swmsandroid.clients.models.Client


class ClientJsonAdapter {

	@FromJson
	fun eventFromJson(eventJson: ClientJsonTemplate): Client {

		val id = eventJson.id
		val name = eventJson.name
		val contactData = eventJson.contactData
		return Client(id, name, contactData)
	}

	@ToJson
	fun eventToJson(client: Client): ClientJsonTemplate {
		val json = ClientJsonTemplate()
		json.id = client.id
		json.name = client.name
		json.contactData = client.contactData
		return json
	}
}