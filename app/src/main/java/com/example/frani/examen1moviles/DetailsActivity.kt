package com.example.frani.examen1moviles

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    var autor: Autor? = null
    lateinit var adaptador: LibroAdapter
    lateinit var libros: ArrayList<Libro>
    lateinit var dbHandler: DBLibroHandlerAplicacion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        autor = intent.getParcelableExtra("autor")

        txtShowNombreAutor.text = autor?.nombre
        txtShowApellidoAutor.text = autor?.apellido
        txtShowFechaNAutor.text = autor?.fechaNacimiento
        txtShowNumLibAutor.text = autor?.numeroLibros.toString()
        txtShowEcuAutor.text = if(autor?.ecuatoriano == 1) getString(R.string.yes) else getString(R.string.no)

        dbHandler = DBLibroHandlerAplicacion(this)
        libros = dbHandler.getLibrosList(autor?.id!!)

        val layoutManager = LinearLayoutManager(this)
        adaptador = LibroAdapter(libros)
        recycler_view_book.layoutManager = layoutManager
        recycler_view_book.itemAnimator = DefaultItemAnimator()
        recycler_view_book.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(recycler_view_book)

        btnNuevoLibro.setOnClickListener{
            v: View? ->  crearLibro()
        }
    }

    fun crearLibro() {
        val intent = Intent(this, CreateBookActivity::class.java)
        intent.putExtra("tipo", "Create")
        intent.putExtra("idAutor", autor?.id!!)
        startActivity(intent)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var position = adaptador.getPosition()
        var libro = libros[position]

        when (item.itemId) {
            R.id.item_menu_compartir -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/html"
                intent.putExtra(Intent.EXTRA_SUBJECT, "${getString(R.string.libro)} - ${getString(R.string.app_name)}")
                intent.putExtra(Intent.EXTRA_TEXT, "${getString(R.string.isbn)} ${libro.icbn}\n${getString(R.string.name)} ${libro.nombre}\n${getString(R.string.edicion)} ${libro.edicion}\n${getString(R.string.editorial)} ${libro.nombreEditorial}")
                startActivity(intent)
                return true
            }
            R.id.item_menu_editar -> {
                val intent = Intent(this, CreateBookActivity::class.java)
                intent.putExtra("tipo", "Edit")
                intent.putExtra("libro", libro)
                startActivity(intent)
                return true
            }
            R.id.item_menu_borrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(R.string.confirmation)
                        .setPositiveButton(R.string.yes, { dialog, which ->
                            dbHandler.deleteLibro(libro.icbn)
                            finish()
                            startActivity(intent)
                        }
                        )
                        .setNegativeButton(R.string.no, null)
                val dialogo = builder.create()
                dialogo.show()
                return true
            }
            else -> {
                Log.i("menu", "Todos los demas")
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
