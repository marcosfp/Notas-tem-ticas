package com.example.notastematicas.room

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notastematicas.databinding.ActivityRoomBinding
import com.example.notastematicas.room.entidades.nota.Nota
import com.example.notastematicas.room.entidades.nota.NotasRepositorio
import com.example.notastematicas.sqlite.TematicaSpinner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class RoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomBinding
    private lateinit var roomRecyclerViewAdapter: RoomRecyclerViewAdapter
    private lateinit var tematicaSelecionada: TematicaSpinner

    private lateinit var database: RoomBaseDeDatos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(this, RoomBaseDeDatos::class.java, "notas_room").build()


        //Iniciamos el recyclerview
        initRecyclerView()


        //establecemos el binding al boton de crear nota
        binding.roomButtonCrearNota.setOnClickListener { crearNota() }

        //Establecemos los valores del spinner
//        cargarSpinner()
    }

    private fun initRecyclerView() {


        val context = this
        lifecycleScope.launch {
            val notas = database.notaDao().obtenerTodasLasNotas()
            roomRecyclerViewAdapter = RoomRecyclerViewAdapter(notas)


            //Configuramos el RecylcerView
            binding.roomRecyclerViewNotas.setHasFixedSize(true)
            binding.roomRecyclerViewNotas.layoutManager = LinearLayoutManager(context)
            binding.roomRecyclerViewNotas.adapter = roomRecyclerViewAdapter
        }
    }


    private fun crearNota() {
        if (binding.roomEditTextoNota.text.isNotBlank()) {

            var notasDao = database.notaDao()


            lifecycleScope.launch {
                notasDao.insertarNota(Nota(texto = binding.roomEditTextoNota.text.toString()))
                binding.roomEditTextoNota.text.clear()
                roomRecyclerViewAdapter.listaPuntuacion = notasDao.obtenerTodasLasNotas()
                roomRecyclerViewAdapter.notifyDataSetChanged()

            }


        } else {
            Toast.makeText(this, "Rellena el campo", Toast.LENGTH_SHORT).show();
        }
    }


//    private fun cargarSpinner() {
//        var listaTematicas = notasDBHelper.obtenerTematicas()
//        val spinnerAdapter =
//            ArrayAdapter<TematicaSpinner>(
//                this,
//                android.R.layout.simple_spinner_item,
//                listaTematicas
//            )
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.roomSpinnerTematica.adapter = spinnerAdapter
//        binding.roomSpinnerTematica.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    tematicaSelecionada = TematicaSpinner(
//                        spinnerAdapter.getItem(position)?.idTematica ?: 0,
//                        spinnerAdapter.getItem(position)?.textoTematica ?: ""
//                    )
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    TODO("Not yet implemented")
//                }
//            }
//    }

}