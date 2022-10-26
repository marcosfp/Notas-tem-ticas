package com.example.notastematicas.sqlite

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notastematicas.databinding.ActivitySqliteBinding


class SQLiteActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySqliteBinding

    private lateinit var notasDBHelper: MiBDOpenHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var miSQLiteRecyclerViewAdapter: SQLiteRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySqliteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Iniciamos el recyclerview
        initRecyclerView()

        //establecemos el binding al boton de crear nota
        binding.buttonCrearNota.setOnClickListener { crearNota() }
    }

    fun initRecyclerView() {

        //Obtnemos las notas de la base de datos
        notasDBHelper = MiBDOpenHelper(this, null)
//        db = notasDBHelper.readableDatabase
//        val cursor: Cursor = db.rawQuery("SELECT * FROM ${MiBDOpenHelper.TABLA_NOTAS}", null)
        val cursor = notasDBHelper.obtenerNotas()
        miSQLiteRecyclerViewAdapter = SQLiteRecyclerViewAdapter()
        miSQLiteRecyclerViewAdapter.SQLiteRecyclerViewAapter(this, cursor)
        //Configuramos el RecylcerView
        binding.recyclerViewNotas.setHasFixedSize(true)
        binding.recyclerViewNotas.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewNotas.adapter = miSQLiteRecyclerViewAdapter
    }


    private fun crearNota() {
        if (binding.editTextoNota.text.isNotBlank()) {
            notasDBHelper.crearNota(binding.editTextoNota.text.toString())
            binding.editTextoNota.text.clear()

            //Como hemos añadido nuevos datos. Tenemos que volver a ahcer la consulta
            //Y llamar a notify datasetchanged para que se recargue la interfaz
            val cursor = notasDBHelper.obtenerNotas()
            miSQLiteRecyclerViewAdapter.SQLiteRecyclerViewAapter(this, cursor)

            miSQLiteRecyclerViewAdapter.notifyDataSetChanged()


        } else {
            Toast.makeText(this, "Rellena el campo", Toast.LENGTH_SHORT).show();
        }
    }


}