package com.example.notastematicas.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notastematicas.room.entidades.nota.Nota
import com.example.notastematicas.room.entidades.nota.NotaDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Nota::class), version = 1, exportSchema = false)
abstract class RoomBaseDeDatos : RoomDatabase() {

    abstract fun notaDao(): NotaDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: RoomBaseDeDatos? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): RoomBaseDeDatos {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomBaseDeDatos::class.java,
                    "basededatos_notastematicas"
                )
                .fallbackToDestructiveMigration()
                .addCallback(RoomBDCallback(scope))
                .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        class RoomBDCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.notaDao())
                    }
                }
            }

            suspend fun populateDatabase(notaDao: NotaDao) {
                // Borramos el contenido
                notaDao.borrarTodasLasNotas()

                // Añadimos una nota
                var nota = Nota(texto = "1º Nota")
                notaDao.insertatNota(nota)

            }
        }

    }
}