package com.example.notastematicas.sqlite

import android.R
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notastematicas.databinding.ActivitySqliteBinding


class SQLiteActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySqliteBinding

    private lateinit var notasDBHelper: MiBDOpenHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var miSQLiteRecyclerViewAdapter: SQLiteRecyclerViewAdapter
    private lateinit var tematicaSelecionada: TematicaSpinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySqliteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Iniciamos el recyclerview
        initRecyclerView()

        //establecemos el binding al boton de crear nota
        binding.buttonCrearNota.setOnClickListener { crearNota() }

        //Establecemos los valores del spinner
        cargarSpinner()
    }

    private fun initRecyclerView() {

        //Obtnemos las notas de la base de datos
        notasDBHelper = MiBDOpenHelper(this, null)
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
            notasDBHelper.crearNota(
                binding.editTextoNota.text.toString(),
                binding.spinnerTematica.selectedItem.toString()
            )
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


    private fun cargarSpinner() {
        var listaTematicas = notasDBHelper.obtenerTematicas()
        val spinnerAdapter =
            ArrayAdapter<TematicaSpinner>(this, R.layout.simple_spinner_item, listaTematicas)
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerTematica.adapter = spinnerAdapter
        binding.spinnerTematica.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    tematicaSelecionada = TematicaSpinner(
                        spinnerAdapter.getItem(position)?.idTematica ?: 0,
                        spinnerAdapter.getItem(position)?.textoTematica ?: ""
                    )
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
    }
}
