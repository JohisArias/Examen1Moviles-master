package com.example.jpas.examen1moviles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.jpas.examen1moviles.R.id.*

class DetailsBookActivity : AppCompatActivity() {

    var libro: Libro? = null

    //override fun...
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_book)

        libro = intent.getParcelableExtra("libro")

        txtShowIsbn.text = libro?.isbn.toString()
        txtShowNombreLibro.text = libro?.nombre
        txtShowNumPagLibro.text = libro?.numeroPaginas.toString()
        txtShowEdicLibro.text = libro?.edicion.toString()
        txtShowFechaPLibro.text = libro?.fechaPublicacion
        txtShowEditorialLibro.text = libro?.Editorial
    }
}
