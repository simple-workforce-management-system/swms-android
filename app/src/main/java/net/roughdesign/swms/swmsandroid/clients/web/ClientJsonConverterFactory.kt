package net.roughdesign.swms.swmsandroid.clients.web

import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.utilities.json.JsonConverter

class ClientJsonConverterFactory {


	fun createRepoModelConverter(): JsonConverter<Client> {
		val moshi = createMoshi()
		val modelAdapter: JsonAdapter<Client> = createModelAdapter(moshi)
		val modelListAdapter: JsonAdapter<ArrayList<Client>> = createModelListAdapter(moshi)
		return JsonConverter(modelAdapter, modelListAdapter)
	}


	private fun createMoshi(): Moshi {
		return Moshi.Builder()
			.add(ClientJsonAdapter())
			.build()
	}


	private fun createModelAdapter(moshi: Moshi): JsonAdapter<Client> {
		val typeToken = object : TypeToken<Client>() {}.type
		return moshi.adapter(typeToken)
	}


	private fun createModelListAdapter(moshi: Moshi): JsonAdapter<ArrayList<Client>> {
		val listTypeToken = object : TypeToken<ArrayList<Client>>() {}.type
		return moshi.adapter(listTypeToken)
	}
}

