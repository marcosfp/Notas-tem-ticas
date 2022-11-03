package com.example.notastematicas.room.entidades.nota

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface  NotaDao {

    @Query("SELECT * FROM tabla_notas ORDER BY texto_nota ASC")
    suspend fun obtenerTodasLasNotas(): List<Nota>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarNota(nota: Nota)

    @Query("DELETE FROM tabla_notas")
    suspend fun borrarTodasLasNotas()

    @Query("DELETE FROM tabla_notas WHERE id_nota =:id_nota")
    suspend fun borrarNotasPorIdById(id_nota:Int)


}