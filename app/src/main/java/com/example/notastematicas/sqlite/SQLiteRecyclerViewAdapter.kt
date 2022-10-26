package com.example.notastematicas.sqlite

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notastematicas.R

class SQLiteRecyclerViewAdapter() : RecyclerView.Adapter<NotasViewHolder>() {

    private lateinit var context: Context
    private lateinit var cursor: Cursor

    fun SQLiteRecyclerViewAapter(context: Context, cursor: Cursor) {
        this.context = context
        this.cursor = cursor
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NotasViewHolder(layoutInflater.inflate(R.layout.item_lista, parent, false))
    }

    //Renederizamos cada elemento de la lista
    override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
        cursor.moveToPosition(position)
        holder.render(cursor.getInt(0),cursor.getString(1))
    }

    //Obtenemos el tamaño de la lista
    override fun getItemCount(): Int {
        return cursor.count

    }

}