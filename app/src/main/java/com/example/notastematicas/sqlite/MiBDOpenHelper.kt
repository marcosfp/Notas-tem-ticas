package com.example.notastematicas.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class MiBDOpenHelper(contex: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(contex, DATABASE_NAME, factory, DATABASE_VERSION) {

    val TAG = "SQLite"

    companion object {
        val DATABASE_VERSION = 2
        val DATABASE_NAME = "notas_tematica.db"
        val TABLA_NOTAS = "notas"
        val NOTAS_C_ID_ = "id_nota"
        val NOTAS_C_TEXTO = "texto_nota"
        val TABLA_TEMATICAS = "tematicas"
        val TEMATICAS_C_ID = "id_tematica"
        val TEMATICAS_C_TEXTO = "texto_tematica"
    }

    //para recrear las base de datos es necesarios borrar los ficheros sobre /data/data/nombre.aplicacion/databases
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            var crearTablaNotas = "CREATE TABLE $TABLA_NOTAS " +
                    "($NOTAS_C_ID_ INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NOTAS_C_TEXTO TEXT," +
                    "$TEMATICAS_C_ID INTEGER)"
            var createTablaTematicas =
                "CREATE TABLE $TABLA_TEMATICAS " +
                        "($TEMATICAS_C_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "$TEMATICAS_C_TEXTO TEXT ," +
                        "FOREIGN KEY ($TEMATICAS_C_ID) " +
                        "REFERENCES $TABLA_TEMATICAS ($TEMATICAS_C_ID)) ;"
            var tematicas_Trabajo =
                "INSERT INTO $TABLA_TEMATICAS ($TEMATICAS_C_TEXTO) VALUES ('Trabajo');"
            var tematicas_Amigos =
                "INSERT INTO $TABLA_TEMATICAS ($TEMATICAS_C_TEXTO) VALUES ('Amigos');"
            var tematicas_Hogar =
                "INSERT INTO $TABLA_TEMATICAS ($TEMATICAS_C_TEXTO) VALUES ('Hogar');"
            var tematicas_Ocio =
                "INSERT INTO $TABLA_TEMATICAS ($TEMATICAS_C_TEXTO) VALUES ('Ocio');"
            var tematicas_Salud =
                "INSERT INTO $TABLA_TEMATICAS ($TEMATICAS_C_TEXTO) VALUES ('Salud');"
            var tematicas_Deporte =
                "INSERT INTO $TABLA_TEMATICAS ($TEMATICAS_C_TEXTO) VALUES ('Deporte');"
            db!!.execSQL(createTablaTematicas)
            db!!.execSQL(crearTablaNotas)
            db!!.execSQL(tematicas_Trabajo)
            db!!.execSQL(tematicas_Amigos)
            db!!.execSQL(tematicas_Hogar)
            db!!.execSQL(tematicas_Ocio)
            db!!.execSQL(tematicas_Salud)
            db!!.execSQL(tematicas_Deporte)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onCreate)", e.message.toString())
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            val dropTablaTematicas = "DROP TABLE IF EXISTS $TABLA_TEMATICAS"
            val dropTablaNotas = "DROP TABLE IF EXISTS $TABLA_NOTAS"
            db!!.execSQL(dropTablaNotas)
            db!!.execSQL(dropTablaTematicas)
            onCreate(db)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onUpgrade)", e.message.toString())
        }
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        Log.e("$TAG (onOpen)", "Base de datos abierta")

    }

    fun crearNota(textoNota: String, tematica: String) {
        var idTematica: Int?
        val db = this.writableDatabase
        val cursorTematicas: Cursor = db!!.rawQuery(
            "SELECT ${MiBDOpenHelper.TEMATICAS_C_ID}  FROM ${MiBDOpenHelper.TABLA_TEMATICAS} " +
                    "WHERE ${MiBDOpenHelper.TEMATICAS_C_TEXTO} = ?", arrayOf(tematica)
        )

        if (cursorTematicas.moveToFirst()) {

            idTematica = cursorTematicas.getInt(0)
            cursorTematicas.close()

            val data = ContentValues()
            data.put(NOTAS_C_TEXTO, textoNota)
            data.put(TEMATICAS_C_ID, idTematica)

            db.insert(TABLA_NOTAS, null, data)
            db.close()
        } else {
            cursorTematicas.close()
        }

    }

    fun obtenerNotas(): Cursor {
        val db = this.readableDatabase
        var cursor = db.rawQuery(
            "SELECT" +
                    " nota.${MiBDOpenHelper.NOTAS_C_ID_} ," +
                    " nota.${MiBDOpenHelper.NOTAS_C_TEXTO} ," +
                    " tematica.${MiBDOpenHelper.TEMATICAS_C_TEXTO} " +
                    " FROM ${MiBDOpenHelper.TABLA_NOTAS} nota INNER JOIN ${MiBDOpenHelper.TABLA_TEMATICAS} tematica " +
                    "ON  nota.${MiBDOpenHelper.TEMATICAS_C_ID} = tematica.${MiBDOpenHelper.TEMATICAS_C_ID} ",
            null
        )
        return cursor
    }

    //Le pasamos los valores a susbtitutir en la query en un Array de String como segundo parametro del metodo rawquery
    fun obtenerNotasPorTematica(tematica: String): Cursor {
        val db = this.readableDatabase
        var cursor = db.rawQuery(
            "SELECT " +
                    " nota${MiBDOpenHelper.NOTAS_C_ID_} ," +
                    " nota.${MiBDOpenHelper.TEMATICAS_C_TEXTO}" +
                    " tematica.${MiBDOpenHelper.TEMATICAS_C_TEXTO}" +
                    " FROM ${MiBDOpenHelper.TABLA_NOTAS} nota INNER JOIN ${MiBDOpenHelper.TABLA_TEMATICAS} tematica " +
                    "ON  nota.${MiBDOpenHelper.TEMATICAS_C_ID} = tematica.${MiBDOpenHelper.TEMATICAS_C_ID} " +
                    "WHERE ${MiBDOpenHelper.TABLA_TEMATICAS}.${MiBDOpenHelper.TEMATICAS_C_ID} = ?",
            arrayOf(tematica)
        )
        return cursor
    }

    fun obtenerTematicas(): List<TematicaSpinner> {
        val db = this.readableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${MiBDOpenHelper.TABLA_TEMATICAS}", null)
        var lista = mutableListOf<TematicaSpinner>()
        try {
            if (cursor.moveToFirst()) {
                do {
                    val temat = TematicaSpinner()
                    temat.idTematica = cursor.getInt(0)
                    temat.textoTematica = cursor.getString(1)
                    lista.add(temat)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error obteneniendo las tematicas de la base de datos")
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return lista
    }


}

