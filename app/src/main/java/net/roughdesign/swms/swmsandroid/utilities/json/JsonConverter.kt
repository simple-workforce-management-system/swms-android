package net.roughdesign.swms.swmsandroid.utilities.json

import com.squareup.moshi.JsonAdapter


class JsonConverter<T>(
	private val modelAdapter: JsonAdapter<T>,
	private val modelListAdapter: JsonAdapter<ArrayList<T>>
) {

	fun encode(model: T?): ByteArray? {
		if (model == null) return null
		val asJsonString = modelAdapter.toJson(model)
		return asJsonString.toByteArray()
	}


	fun convert(byteArray: ByteArray?): T? {
		if (byteArray == null) return null

		val asString = String(byteArray)
		return modelAdapter.fromJson(asString)
	}


	fun encodeList(model: ArrayList<T>?): ByteArray? {
		if (model == null) return null
		val asJsonString = modelListAdapter.toJson(model)
		return asJsonString.toByteArray()
	}


	fun convertList(byteArray: ByteArray?): ArrayList<T>? {
		if (byteArray == null) return null
		val asString = String(byteArray)
		return modelListAdapter.fromJson(asString)
	}
}