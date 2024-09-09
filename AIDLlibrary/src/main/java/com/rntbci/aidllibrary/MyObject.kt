package com.rntbci.aidllibrary

import android.os.Parcel
import android.os.Parcelable

data class MyObject(private val name: String, private val age: Int) : Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyObject> {
        override fun createFromParcel(parcel: Parcel): MyObject {
            return MyObject(parcel)
        }

        override fun newArray(size: Int): Array<MyObject?> {
            return arrayOfNulls(size)
        }
    }

}