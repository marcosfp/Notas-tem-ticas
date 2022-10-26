package com.example.notastematicas.sqlite

import android.database.Cursor
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySqliteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notasDBHelper = MiBDOpenHelper(this, null)
        db = notasDBHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${MiBDOpenHelper.TABLA_NOTAS}", null)
        val miSQLiteRecyclerViewAapter = SQLiteRecyclerViewAdapter()
        miSQLiteRecyclerViewAapter.SQLiteRecyclerViewAapter(this, cursor)
        binding.recyclerViewNotas.setHasFixedSize(true)
        binding.recyclerViewNotas.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewNotas.adapter = miSQLiteRecyclerViewAapter

        binding.buttonCrearNota.setOnClickListener { crearNota() }
    }

    private fun crearNota() {
        if (binding.editTextoNota.text.isNotBlank()) {
            notasDBHelper.crearNota(binding.editTextoNota.text.toString())
            binding.editTextoNota.text.clear()


        } else {
            Toast.makeText(this, "Rellena el campo", Toast.LENGTH_SHORT).show();
        }
    }


}