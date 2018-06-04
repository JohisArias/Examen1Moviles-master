package com.example.jpas.examen1moviles

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LibroDB {
    companion object {
        val NombreDB = "Libros"
        val TablaLibro = "libro"
        val ISBN = "isbn"
        val NombreLibro = "nombre"
        val NroPaginas = "numeroPaginas"
        val NroEdicion = "edicion"
        val FechaPublicacion = "fechaPublicacion"
        val Editorial = "Editorial"
        val IdAutor = "IdAutor"
    }
}

class DBLibroHandlerAplicacion(context: Context) : SQLiteOpenHelper(context, LibroDB.NombreDB, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE ${LibroDB.TablaLibro} (${LibroDB.ISBN} INTEGER PRIMARY KEY, ${LibroDB.NombreLibro} VARCHAR(50),${LibroDB.NroPaginas} INTEGER,${LibroDB.NroEdicion} INTEGER, ${LibroDB.FechaPublicacion} VARCHAR(20), ${LibroDB.Editorial} VARCHAR(20),  ${LibroDB.IdAutor} INTEGER)"
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarLibro(libro: Libro) {
        val dbWriteable = writableDatabase
        val contentValues = ContentValues()

        contentValues.put(LibroDB.ISBN, libro.isbn)
        contentValues.put(LibroDB.NombreLibro, libro.nombre)
        contentValues.put(LibroDB.NroPaginas, libro.numeroPaginas)
        contentValues.put(LibroDB.NroEdicion, libro.edicion)
        contentValues.put(LibroDB.FechaPublicacion, libro.fechaPublicacion)
        contentValues.put(LibroDB.Editorial, libro.Editorial)
        contentValues.put(LibroDB.IdAutor, libro.autorID)

        val logcat = dbWriteable.insert(LibroDB.TablaLibro, null, contentValues)

        //Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $logcat")

        dbWriteable.close()
    }

    fun actualizarLibro(libro: Libro) {
        val dbWriteable = writableDatabase
        val contentValues = ContentValues()

        contentValues.put(LibroDB.ISBN, libro.isbn)
        contentValues.put(LibroDB.NombreLibro, libro.nombre)
        contentValues.put(LibroDB.NroPaginas, libro.numeroPaginas)
        contentValues.put(LibroDB.NroEdicion, libro.edicion)
        contentValues.put(LibroDB.FechaPublicacion, libro.fechaPublicacion)
        contentValues.put(LibroDB.Editorial, libro.Editorial)
        contentValues.put(LibroDB.IdAutor, libro.autorID)

        val whereClause = "${LibroDB.ISBN} = ${libro.isbn}"
        val logcat = dbWriteable.update(LibroDB.TablaLibro, contentValues, whereClause, null)

        //Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $logcat")

        dbWriteable.close()
    }

    fun eliminarLibro(isbn: Int): Boolean {
        val dbWriteable = writableDatabase
        val whereClause = "${LibroDB.ISBN} = $isbn"
        return dbWriteable.delete(LibroDB.TablaLibro, whereClause, null) > 0
    }

    fun getLibrosList(idAutor: Int): ArrayList<Libro> {
        var lista = ArrayList<Libro>()
        val dbReadable = readableDatabase
        val query = "SELECT * FROM ${LibroDB.TablaLibro}"
        val resultado = dbReadable.rawQuery(query, null)

        if (resultado.moveToFirst()) {
            do {
                val isbn = resultado.getString(0).toInt()
                val nombre = resultado.getString(1)
                val numeroPaginas = resultado.getString(2).toInt()
                val edicion = resultado.getString(3).toInt()
                val fechaPublicacion = resultado.getString(4)
                val editorial = resultado.getString(5)
                val IdAutor = resultado.getString(6).toInt()

                lista.add(Libro(isbn, nombre, numeroPaginas, edicion, fechaPublicacion, editorial, IdAutor))
            } while (resultado.moveToNext())
        }

        resultado.close()
        dbReadable.close()

        return lista
    }

}
