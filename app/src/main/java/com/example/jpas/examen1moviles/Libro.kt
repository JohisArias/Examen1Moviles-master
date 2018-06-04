package com.example.jpas.examen1moviles

import android.os.Parcel
import android.os.Parcelable

class Libro(var isbn: Int, var nombre: String, var numeroPaginas: Int, var edicion: Int, var fechaPublicacion: String, var Editorial: String, var autorID: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(destino: Parcel?, p1: Int) {
        destino?.writeInt(isbn)
        destino?.writeString(nombre)
        destino?.writeInt(numeroPaginas)
        destino?.writeInt(edicion)
        destino?.writeString(fechaPublicacion)
        destino?.writeString(Editorial)
        destino?.writeInt(autorID)
    }

    companion object CREATOR : Parcelable.Creator<Libro> {
        override fun createFromParcel(parcel: Parcel): Libro {
            return Libro(parcel)
        }

        override fun newArray(size: Int): Array<Libro?> {
            return arrayOfNulls(size)
        }
    }

}