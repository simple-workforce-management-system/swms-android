package net.roughdesign.swms.swmsandroid.utilities.web.repositories

import com.squareup.moshi.JsonAdapter


class JsonConverter<T>(
	private val modelAdapter: JsonAdapter<T>,
	private val modelListAdapter: JsonAdapter<List<T>>
) {

	fun serialize(model: T?): ByteArray? {
		if (model == null) return null
		val asJsonString = modelAdapter.toJson(model)
		return asJsonString.toByteArray()
	}


	fun deserialize(byteArray: ByteArray?): T? {
		if (byteArray == null) return null

		val asString = String(byteArray)
		return modelAdapter.fromJson(asString)
	}


	fun serializeList(model: List<T>?): ByteArray? {
		if (model == null) return null
		val asJsonString = modelListAdapter.toJson(model)
		return asJsonString.toByteArray()
	}


	fun deserializeList(byteArray: ByteArray?): List<T>? {
		if (byteArray == null) return null
		val asString = String(byteArray)
		return modelListAdapter.fromJson(asString)
	}
}