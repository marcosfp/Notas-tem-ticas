package com.example.notastematicas.sqlite

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notastematicas.R

class NotasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val nota_id = itemView.findViewById<TextView>(R.id.textNotaId)
    val nota_texto = itemView.findViewById<TextView>(R.id.textNotaTexto)
    val nota_tematica = itemView.findViewById<TextView>(R.id.textNotaTematica)

    fun render(id_nota: Int, texto_nota: String,tematica_nota :String) {
        nota_id.text = id_nota.toString()
        nota_texto.text = texto_nota
        nota_tematica.text  =tematica_nota
    }

}