package com.magnus.criptomoedas

import android.os.Parcel
import android.os.Parcelable

data class Ativo(var id: Int?, val nome: String?, val codigo: String?, var qtd: Double?) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble()

    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        this!!.id?.let { parcel.writeInt(it) }
        parcel.writeString(nome)
        parcel.writeString(codigo)
        if (qtd != null) {
            parcel.writeDouble(qtd!!)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ativo> {
        override fun createFromParcel(parcel: Parcel): Ativo {
            return Ativo(parcel)
        }

        override fun newArray(size: Int): Array<Ativo?> {
            return arrayOfNulls(size)
        }
    }
}