package com.example.notastematicas.room

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notastematicas.R
import com.example.notastematicas.room.entidades.nota.Nota
import com.example.notastematicas.sqlite.NotasViewHolder
import kotlinx.coroutines.flow.Flow

class RoomRecyclerViewAdapter (var listaPuntuacion: List<Nota>) : RecyclerView.Adapter<NotasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NotasViewHolder(layoutInflater.inflate(R.layout.item_lista, parent, false))
    }

    //Renederizamos cada elemento de la lista
    override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
        val item = listaPuntuacion[position]
        holder.render(item.idNota, item.texto, "TODO")
    }

    //Obtenemos el tamaño de la lista
    override fun getItemCount(): Int {
        return listaPuntuacion.size
    }

}