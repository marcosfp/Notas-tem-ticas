package com.example.notastematicas.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MiBDOpenHelper(contex: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(contex, DATABASE_NAME, factory, DATABASE_VERSION) {

    val TAG = "SQLite"

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "notas_tematica.db"
        val TABLA_NOTAS = "notas"
        val COLUMNA_ID = "id_nota"
        val COLUMNA_TEXTO = "texto_nota"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            var crearTablaNotas =
                "CREATE TABLE $TABLA_NOTAS ($COLUMNA_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMNA_TEXTO TEXT)"
            db!!.execSQL(crearTablaNotas)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onCreate)", e.message.toString())
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            val dropTablaNotas = "DROP TABLE IF EXISTS $TABLA_NOTAS"
            db!!.execSQL(dropTablaNotas)
            onCreate(db)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onUpgrade)", e.message.toString())
        }
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        Log.e("$TAG (onOpen)", "Base de datos abierta")

    }

    fun crearNota(textoNota: String) {
        val data = ContentValues()
        data.put(COLUMNA_TEXTO,textoNota)

        val db= this.writableDatabase
        db.insert(TABLA_NOTAS,null,data)
        db.close()
    }
}

