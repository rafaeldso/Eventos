package com.rafaeldso.eventos.model

import android.os.Parcel
import android.os.Parcelable

data class Event(

    val date: Long,
    val description: String?,
    val image: String?,
    val longitude: Double,
    val latitude: Double,
    val price: Double,
    val title: String?,
    val id: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(

        date = parcel.readLong(),
        description = parcel.readString(),
        image = parcel.readString(),
        longitude = parcel.readDouble(),
        latitude = parcel.readDouble(),
        price = parcel.readDouble(),
        title = parcel.readString(),
        id = parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(date)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeDouble(longitude)
        parcel.writeDouble(latitude)
        parcel.writeDouble(price)
        parcel.writeString(title)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Event(nome='$title')"
    }

    fun urlImagem():String?{
        return this.image?.replace("http:", "https:")
    }

}
