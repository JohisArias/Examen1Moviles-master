package com.example.frani.examen1moviles

import android.os.Parcel
import android.os.Parcelable

class Autor(var id: Int, var nombre: String, var apellido: String, var fechaNacimiento: String, var numeroLibros: Int, var ecuatoriano: Int): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(destino: Parcel?, p1: Int) {
        destino?.writeInt(id)
        destino?.writeString(nombre)
        destino?.writeString(apellido)
        destino?.writeString(fechaNacimiento)
        destino?.writeInt(numeroLibros)
        destino?.writeInt(ecuatoriano)
    }

    companion object CREATOR : Parcelable.Creator<Autor> {
        override fun createFromParcel(parcel: Parcel): Autor {
            return Autor(parcel)
        }

        override fun newArray(size: Int): Array<Autor?> {
            return arrayOfNulls(size)
        }
    }

}