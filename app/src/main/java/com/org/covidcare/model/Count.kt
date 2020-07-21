package com.org.covidcare.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by ishwari s on 6/23/2020.
 */
class Count(
    val region_name: String?, val region_flag: String?, val cases_confirmed: Int,
    val case_recovered: Int, val case_death: Int, val case_active: Int,
    val todayCases: Int, val todayRecovered: Int, val todayDeaths: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Count> {
        override fun createFromParcel(parcel: Parcel): Count {
            return Count(parcel)
        }

        override fun newArray(size: Int): Array<Count?> {
            return arrayOfNulls(size)
        }
    }
}