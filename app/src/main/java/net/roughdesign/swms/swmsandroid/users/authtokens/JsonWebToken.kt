package net.roughdesign.swms.swmsandroid.users.authtokens

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class JsonWebToken(val asString: String) : Parcelable