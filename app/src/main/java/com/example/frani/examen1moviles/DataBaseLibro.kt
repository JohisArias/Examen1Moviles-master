package com.example.frani.examen1moviles

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseLibro {
    companion object {
        val DB_NAME = "Libros"
        val TABLE_NAME = "libro"
        val CAMPO_ICBN = "icbn"
        val CAMPO_NOMBRE = "nombre"
        val CAMPO_NUMEROPAGINAS = "numeroPaginas"
        val CAMPO_EDICION = "edicion"
        val CAMPO_FECHAPUBLICACION = "fechaPublicacion"
        val CAMPO_NOMBREEDITORIAL = "nombreEditorial"
        val CAMPO_AUTORID = "autorID"
    }
}

class DBLibroHandlerAplicacion(context: Context) : SQLiteOpenHelper(context, DataBaseLibro.DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE ${DataBaseLibro.TABLE_NAME} (${DataBaseLibro.CAMPO_ICBN} INTEGER PRIMARY KEY, ${DataBaseLibro.CAMPO_NOMBRE} VARCHAR(50),${DataBaseLibro.CAMPO_NUMEROPAGINAS} INTEGER,${DataBaseLibro.CAMPO_EDICION} INTEGER, ${DataBaseLibro.CAMPO_FECHAPUBLICACION} VARCHAR(20), ${DataBaseLibro.CAMPO_NOMBREEDITORIAL} VARCHAR(20),  ${DataBaseLibro.CAMPO_AUTORID} INTEGER)"
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarLibro(libro: Libro) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBaseLibro.CAMPO_ICBN, libro.icbn)
        cv.put(DataBaseLibro.CAMPO_NOMBRE, libro.nombre)
        cv.put(DataBaseLibro.CAMPO_NUMEROPAGINAS, libro.numeroPaginas)
        cv.put(DataBaseLibro.CAMPO_EDICION, libro.edicion)
        cv.put(DataBaseLibro.CAMPO_FECHAPUBLICACION, libro.fechaPublicacion)
        cv.put(DataBaseLibro.CAMPO_NOMBREEDITORIAL, libro.nombreEditorial)
        cv.put(DataBaseLibro.CAMPO_AUTORID, libro.autorID)

        val resultado = dbWriteable.insert(DataBaseLibro.TABLE_NAME, null, cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun updateLibro(libro: Libro) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBaseLibro.CAMPO_ICBN, libro.icbn)
        cv.put(DataBaseLibro.CAMPO_NOMBRE, libro.nombre)
        cv.put(DataBaseLibro.CAMPO_NUMEROPAGINAS, libro.numeroPaginas)
        cv.put(DataBaseLibro.CAMPO_EDICION, libro.edicion)
        cv.put(DataBaseLibro.CAMPO_FECHAPUBLICACION, libro.fechaPublicacion)
        cv.put(DataBaseLibro.CAMPO_NOMBREEDITORIAL, libro.nombreEditorial)
        cv.put(DataBaseLibro.CAMPO_AUTORID, libro.autorID)

        val whereClause = "${DataBaseLibro.CAMPO_ICBN} = ${libro.icbn}"
        val resultado = dbWriteable.update(DataBaseLibro.TABLE_NAME, cv, whereClause, null)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun deleteLibro(icbn: Int): Boolean {
        val dbWriteable = writableDatabase
        val whereClause = "${DataBaseLibro.CAMPO_ICBN} = $icbn"
        return dbWriteable.delete(DataBaseLibro.TABLE_NAME, whereClause, null) > 0
    }

    fun getLibrosList(idAutor: Int): ArrayList<Libro> {
        var lista = ArrayList<Libro>()
        val dbReadable = readableDatabase
        val query = "SELECT * FROM ${DataBaseLibro.TABLE_NAME}"
        val resultado = dbReadable.rawQuery(query, null)

        if (resultado.moveToFirst()) {
            do {
                val icbn = resultado.getString(0).toInt()
                val nombre = resultado.getString(1)
                val numeroPaginas = resultado.getString(2).toInt()
                val edicion = resultado.getString(3).toInt()
                val fechaPublicacion = resultado.getString(4)
                val nombreEditorial = resultado.getString(5)
                val autorID = resultado.getString(6).toInt()

                lista.add(Libro(icbn, nombre, numeroPaginas, edicion, fechaPublicacion, nombreEditorial, autorID))
            } while (resultado.moveToNext())
        }

        resultado.close()
        dbReadable.close()

        return lista
    }

}
