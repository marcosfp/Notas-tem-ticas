package com.example.notastematicas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notastematicas.databinding.ActivityMainBinding
import com.example.notastematicas.databinding.ActivitySqliteBinding
import com.example.notastematicas.room.RoomActivity
import com.example.notastematicas.sqlite.SQLiteActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRoom.setOnClickListener{
            val roomActivity =Intent(this,RoomActivity::class.java)
            startActivity(roomActivity)
        }
        binding.buttonSqlite.setOnClickListener{
            val sqliteActivity =Intent(this,SQLiteActivity::class.java)
            startActivity(sqliteActivity)
        }


    }
}