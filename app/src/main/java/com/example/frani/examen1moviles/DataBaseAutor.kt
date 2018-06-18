package com.example.frani.examen1moviles

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseAutor {
    companion object {
        val DB_NAME = "autorLibro"
        val TABLE_NAME = "autors"
        val CAMPO_ID = "id"
        val CAMPO_NOMBRE = "nombre"
        val CAMPO_APELLIDO = "apellido"
        val CAMPO_FECHANACIMIENTO = "fechaNacimiento"
        val CAMPO_NUMEROLIBROS = "numeroLibros"
        val CAMPO_ECUATORIANO = "ecuatoriano"
    }
}

class DBAutorHandlerAplicacion(context: Context) : SQLiteOpenHelper(context, DataBaseAutor.DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE ${DataBaseAutor.TABLE_NAME} (${DataBaseAutor.CAMPO_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${DataBaseAutor.CAMPO_NOMBRE} VARCHAR(50),${DataBaseAutor.CAMPO_APELLIDO} VARCHAR(50),${DataBaseAutor.CAMPO_FECHANACIMIENTO} VARCHAR(20), ${DataBaseAutor.CAMPO_NUMEROLIBROS} INTEGER, ${DataBaseAutor.CAMPO_ECUATORIANO} INTEGER)"
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarAutor(autor: Autor) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBaseAutor.CAMPO_NOMBRE, autor.nombre)
        cv.put(DataBaseAutor.CAMPO_APELLIDO, autor.apellido)
        cv.put(DataBaseAutor.CAMPO_FECHANACIMIENTO, autor.fechaNacimiento)
        cv.put(DataBaseAutor.CAMPO_NUMEROLIBROS, autor.numeroLibros)
        cv.put(DataBaseAutor.CAMPO_ECUATORIANO, autor.ecuatoriano)

        val resultado = dbWriteable.insert(DataBaseAutor.TABLE_NAME, null, cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun updateAutor(autor: Autor) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBaseAutor.CAMPO_NOMBRE, autor.nombre)
        cv.put(DataBaseAutor.CAMPO_APELLIDO, autor.apellido)
        cv.put(DataBaseAutor.CAMPO_FECHANACIMIENTO, autor.fechaNacimiento)
        cv.put(DataBaseAutor.CAMPO_NUMEROLIBROS, autor.numeroLibros)
        cv.put(DataBaseAutor.CAMPO_ECUATORIANO, autor.ecuatoriano)

        val whereClause = "${DataBaseAutor.CAMPO_ID} = ${autor.id}"
        val resultado = dbWriteable.update(DataBaseAutor.TABLE_NAME, cv, whereClause, null)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun deleteAutor(id: Int): Boolean {
        val dbWriteable = writableDatabase
        val whereClause = "${DataBaseAutor.CAMPO_ID} = $id"
        return dbWriteable.delete(DataBaseAutor.TABLE_NAME, whereClause, null) > 0
    }

    fun getAutorsList(): ArrayList<Autor> {
        var lista = ArrayList<Autor>()
        val dbReadable = readableDatabase
        val query = "SELECT * FROM ${DataBaseAutor.TABLE_NAME}"
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
