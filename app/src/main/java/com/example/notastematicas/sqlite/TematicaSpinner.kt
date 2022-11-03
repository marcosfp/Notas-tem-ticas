package com.example.notastematicas.sqlite

data class TematicaSpinner(
    var idTematica: Int = 0,
    var textoTematica: String = ""
) {

    override fun toString(): String {
        return textoTematica
    }
}