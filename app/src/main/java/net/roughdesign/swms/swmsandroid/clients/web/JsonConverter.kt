package net.roughdesign.swms.swmsandroid.clients.web

interface JsonConverter<T> {
    fun toJson(value: T): String
    fun fromJson(value: String): T
}