package com.example.jpas.examen1moviles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.jpas.examen1moviles.R.id.*

class CreateBookActivity : AppCompatActivity() {

    lateinit var dbHandler: DBLibroHandlerAplicacion
    var idAutor: Int = 0
    var libro: Libro? = null
    var tipo = false

    //override fun...
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_book)

        val type = intent.getStringExtra("tipo")

        if (type.equals("Edit")) {
            textViewLibro.text = getString(R.string.edit_book)
            libro = intent.getParcelableExtra("libro")
            fillFields()
            tipo = true
        }

        idAutor = intent.getIntExtra("idAutor", 0)

        dbHandler = DBLibroHandlerAplicacion(this)

        btnCrearLibro.setOnClickListener{
            v: View? ->  crearLibro()
        }
    }

    fun fillFields() {
        txtISBNLibro.setText(libro?.isbn.toString())
        txtNombreLibro.setText(libro?.nombre)
        txtNPagLibro.setText(libro?.numeroPaginas.toString())
        txtEdicionLibro.setText(libro?.edicion.toString())
        txtFechaPLibro.setText(libro?.fechaPublicacion)
        txtEditorialLibro.setText(libro?.Editorial)
    }

    fun crearLibro() {
        var isbn = txtISBNLibro.text.toString().toInt()
        var nombre = txtNombreLibro.text.toString()
        var numeroPaginas = txtNPagLibro.text.toString().toInt()
        var edicion = txtEdicionLibro.text.toString().toInt()
        var fechaP = txtFechaPLibro.text.toString()
        var editorial = txtEditorialLibro.text.toString()
        var libro = Libro(isbn, nombre, numeroPaginas, edicion, fechaP, editorial, idAutor)

        if (!tipo) {
            dbHandler.insertarLibro(libro)
        } else {
            dbHandler.actualizarLibro(libro)
        }

        finish()
    }
}
