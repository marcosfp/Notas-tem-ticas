package com.example.notastematicas.room.entidades.nota

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_notas")
class Tematica(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_nota") val idNota: Int = 0,
    @ColumnInfo(name = "texto_nota") val texto: String
) {
}