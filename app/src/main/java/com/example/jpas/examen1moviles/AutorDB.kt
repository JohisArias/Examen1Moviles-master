package com.example.jpas.examen1moviles

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class AutorDB {
    companion object {
        val NombreDB = "autorLibro"
        val TablaAutor = "autor"
        val Id = "id"
        val NombreAutor = "nombre"
        val ApellidoAutor = "apellido"
        val FechaNacimiento = "fechaNacimiento"
        val NroLibros = "numeroLibros"
        val Ecuatoriano = "ecuatoriano"
    }
}

class DBAutorHandlerAplicacion(context: Context) : SQLiteOpenHelper(context, AutorDB.NombreDB, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE ${AutorDB.TablaAutor} (${AutorDB.Id} INTEGER PRIMARY KEY AUTOINCREMENT, ${AutorDB.NombreAutor} VARCHAR(50),${AutorDB.ApellidoAutor} VARCHAR(50),${AutorDB.FechaNacimiento} VARCHAR(20), ${AutorDB.NroLibros} INTEGER, ${AutorDB.Ecuatoriano} INTEGER)"
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarAutor(autor: Autor) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(AutorDB.NombreAutor, autor.nombre)
        cv.put(AutorDB.ApellidoAutor, autor.apellido)
        cv.put(AutorDB.FechaNacimiento, autor.fechaNacimiento)
        cv.put(AutorDB.NroLibros, autor.numeroLibros)
        cv.put(AutorDB.Ecuatoriano, autor.ecuatoriano)

        val logcat = dbWriteable.insert(AutorDB.TablaAutor, null, cv)

        //Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $logcat")

        dbWriteable.close()
    }

    fun actualizarAutor(autor: Autor) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(AutorDB.NombreAutor, autor.nombre)
        cv.put(AutorDB.ApellidoAutor, autor.apellido)
        cv.put(AutorDB.FechaNacimiento, autor.fechaNacimiento)
        cv.put(AutorDB.NroLibros, autor.numeroLibros)
        cv.put(AutorDB.Ecuatoriano, autor.ecuatoriano)

        val whereClause = "${AutorDB.Id} = ${autor.id}"
        val logcat = dbWriteable.update(AutorDB.TablaAutor, cv, whereClause, null)

        //Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $logcat")

        dbWriteable.close()
    }

    fun eliminarAutor(id: Int): Boolean {
        val dbWriteable = writableDatabase
        val whereClause = "${AutorDB.Id} = $id"
        return dbWriteable.delete(AutorDB.TablaAutor, whereClause, null) > 0
    }

    fun getAutorsList(): ArrayList<Autor> {
        var lista = ArrayList<Autor>()
        val dbReadable = readableDatabase
        val query = "SELECT * FROM ${AutorDB.TablaAutor}"
        val resultado = dbReadable.rawQuery(query, null)

        if (resultado.moveToFirst()) {
            do {
                val id = resultado.getString(0).toInt()
                val nombre = resultado.getString(1)
                val apellido = resultado.getString(2)
                val fechaNacimiento = resultado.getString(3)
                val numeroLibros = resultado.getString(4).toInt()
                val ecuatoriano = resultado.getString(5).toInt()

                lista.add(Autor(id, nombre, apellido, fechaNacimiento, numeroLibros, ecuatoriano))
            } while (resultado.moveToNext())
        }

        resultado.close()
        dbReadable.close()

        return lista
    }

}
