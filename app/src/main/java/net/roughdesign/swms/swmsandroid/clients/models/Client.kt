package net.roughdesign.swms.swmsandroid.clients.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Client(
	val id: Long?,
	val name: String?,
	val contactData: String?
) : Parcelable
