package com.example.coachticketbooking.model

import android.os.Parcel
import android.os.Parcelable
import com.example.coachticketbooking.utils.Constants

data class RouteSearchPattern(
    val pickLocation: String,
    val destination: String,
    var date: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: Constants.EMPTY_STRING,
        parcel.readString() ?: Constants.EMPTY_STRING,
        parcel.readString() ?: Constants.EMPTY_STRING
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pickLocation)
        parcel.writeString(destination)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RouteSearchPattern> {
        override fun createFromParcel(parcel: Parcel): RouteSearchPattern {
            return RouteSearchPattern(parcel)
        }

        override fun newArray(size: Int): Array<RouteSearchPattern?> {
            return arrayOfNulls(size)
        }
    }
}