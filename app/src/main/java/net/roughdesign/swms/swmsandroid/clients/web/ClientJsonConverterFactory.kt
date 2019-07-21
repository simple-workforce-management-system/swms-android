package net.roughdesign.swms.swmsandroid.clients.web

import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import net.roughdesign.swms.swmsandroid.clients.models.Client
import net.roughdesign.swms.swmsandroid.utilities.web.repositories.JsonConverter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object ClientJsonConverterFactory {


	fun create(): JsonConverter<Client> {
		val moshi = Moshi.Builder().build()
		val modelAdapter: JsonAdapter<Client> = createModelAdapter(moshi)
		val modelListAdapter: JsonAdapter<List<Client>> = createModelListAdapter(moshi)
		return JsonConverter(modelAdapter, modelListAdapter)
	}


	private fun createModelAdapter(moshi: Moshi): JsonAdapter<Client> {
		val typeToken: Type = object : TypeToken<Client>() {}.type
		return moshi.adapter(typeToken)
	}


	private fun createModelListAdapter(moshi: Moshi): JsonAdapter<List<Client>> {
		val listType: ParameterizedType = Types.newParameterizedType(List::class.java, Client::class.java)
		return moshi.adapter(listType)
	}
}
