package net.roughdesign.swms.swmsandroid

import junit.framework.Assert.assertEquals
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.clients.web.ClientJsonConverterFactory
import net.roughdesign.swms.swmsandroid.utilities.web.repositories.JsonConverter
import org.junit.Test


class ClientJsonConverterFactoryTests {


	private val clientJson = "    {\n" +
			"        \"id\": 1,\n" +
			"        \"name\": \"ricola tup\",\n" +
			"        \"contactData\": \"Test contact data\"\n" +
			"    }"


	private val clientJsonList = "[\n" +
			"    {\n" +
			"        \"id\": 1,\n" +
			"        \"name\": \"ricola tup\",\n" +
			"        \"contactData\": \"Test contact data\"\n" +
			"    },\n" +
			"    {\n" +
			"        \"id\": 7,\n" +
			"        \"name\": \"list filler\",\n" +
			"        \"contactData\": \"why\"\n" +
			"    },\n" +
			"    {\n" +
			"        \"id\": 8,\n" +
			"        \"name\": \"peter\",\n" +
			"        \"contactData\": \"ui\"\n" +
			"    }\n" +
			"]"


	@Test
	fun canParseClient() {
		val jsonConverter: JsonConverter<Client> = ClientJsonConverterFactory.create()
		val asByteArray = clientJson.toByteArray()
		val client: Client? = jsonConverter.deserialize(asByteArray)
		assertEquals("ricola tup", client!!.name)
	}


	@Test
	fun canParseClientArrayList() {
		val jsonConverter: JsonConverter<Client> = ClientJsonConverterFactory.create()
		val asByteArray = clientJsonList.toByteArray()
		val clients: List<Client>? = jsonConverter.deserializeList(asByteArray)
		assertEquals(3, clients!!.size)
		assertEquals("ricola tup", clients!![0].name)
	}
}