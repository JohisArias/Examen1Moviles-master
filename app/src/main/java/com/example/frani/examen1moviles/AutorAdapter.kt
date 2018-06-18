package com.example.frani.examen1moviles

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.TextView

class AutorAdapter(private val autorsList: List<Autor>) : RecyclerView.Adapter<AutorAdapter.MyViewHolder>() {

    private var position: Int = 0

    fun getPosition(): Int {
        return position
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

        var nombre: TextView
        var fechaNacimiento: TextView
        var numeroLibros: TextView
        var detalles: Button
        lateinit var autor: Autor

        init {
            nombre = view.findViewById(R.id.txt_1) as TextView
            fechaNacimiento = view.findViewById(R.id.txt_2) as TextView
            numeroLibros = view.findViewById(R.id.txt_3) as TextView
            detalles = view.findViewById(R.id.btn_1)as Button
            view.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(Menu.NONE, R.id.item_menu_compartir, Menu.NONE, R.string.menu_share)
            menu?.add(Menu.NONE, R.id.item_menu_editar, Menu.NONE, R.string.menu_edit)
            menu?.add(Menu.NONE, R.id.item_menu_borrar, Menu.NONE, R.string.menu_delete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_layout, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val autor = autorsList[position]
        holder.nombre.text = autor.nombre
        holder.fechaNacimiento.text = autor.fechaNacimiento
        holder.numeroLibros.text = autor.numeroLibros.toString()
        holder.autor = autor
        holder.detalles.setOnClickListener{
            v: View ->
            val intent = Intent(v.context, DetailsActivity::class.java)
            intent.putExtra("autor", autor)
            v.context.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener {
            setPosition(holder.adapterPosition)
            false
        }
    }

    override fun getItemCount(): Int {
        return autorsList.size
    }

}