package com.example.notastematicas.room.entidades.nota

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class NotasRepositorio(private val notaDao: NotaDao) {

    val todasLasNotas: Flow<List<Nota>> = notaDao.obTenerTodasLasNotas()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertarNota(nota: Nota){
        notaDao.insertatNota(nota)
    }


}