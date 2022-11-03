package com.example.notastematicas.room

import android.app.Application
import androidx.room.Room
import com.example.notastematicas.room.entidades.nota.Nota
import com.example.notastematicas.room.entidades.nota.NotasRepositorio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RoomBDApplication : Application() {

    val database = Room.databaseBuilder(this, RoomBaseDeDatos::class.java,"notas_room").build()

//    val applicationScope = CoroutineScope(SupervisorJob())
//    // Using by lazy so the database and the repository are only created when they're needed
//    // rather than when the application starts
//    val database by lazy { RoomBaseDeDatos.getDatabase(this,applicationScope) }
//    val repository by lazy {
//        NotasRepositorio(database.notaDao())
//    }

}