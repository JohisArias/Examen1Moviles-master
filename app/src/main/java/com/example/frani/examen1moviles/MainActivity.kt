package com.example.frani.examen1moviles

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_crear.setOnClickListener{
            v: View? ->  irACreateView()
        }

        btn_listar.setOnClickListener{
            v: View? ->  irAListView()
        }
    }

    fun irAListView() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }

    fun irACreateView() {
        val intent = Intent(this, CreateActivity::class.java)
        intent.putExtra("tipo", "Create")
        startActivity(intent)
    }
}
