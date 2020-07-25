package com.magnus.criptomoedas.Obejtos

import android.os.Parcel
import android.os.Parcelable

data class Nome(var nome: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Nome> {
        override fun createFromParcel(parcel: Parcel): Nome {
            return Nome(parcel)
        }

        override fun newArray(size: Int): Array<Nome?> {
            return arrayOfNulls(size)
        }
    }
}