package com.example.frani.examen1moviles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_details_book.*

class DetailsBookActivity : AppCompatActivity() {

    var libro: Libro? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_book)

        libro = intent.getParcelableExtra("libro")

        txtShowIsbn.text = libro?.icbn.toString()
        txtShowNombreLibro.text = libro?.nombre
        txtShowNumPagLibro.text = libro?.numeroPaginas.toString()
        txtShowEdicLibro.text = libro?.edicion.toString()
        txtShowFechaPLibro.text = libro?.fechaPublicacion
        txtShowEditorialLibro.text = libro?.nombreEditorial
    }
}
