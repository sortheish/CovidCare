package com.org.covidcare.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by ishwari s on 8/13/2020.
 */
data class NotificationInfo(
    val notification_id:String?,
    val sender_group: String?,
    val date: Long?,
    val title: String?,
    val image: String?,
    val content: String?,
    val link:String?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    //constructor():this("",0,"","","","")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(notification_id)
        parcel.writeString(sender_group)
        parcel.writeLong(date!!)
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeString(content)
        parcel.writeString(link)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NotificationInfo> {
        override fun createFromParcel(parcel: Parcel): NotificationInfo {
            return NotificationInfo(parcel)
        }

        override fun newArray(size: Int): Array<NotificationInfo?> {
            return arrayOfNulls(size)
        }
    }
}