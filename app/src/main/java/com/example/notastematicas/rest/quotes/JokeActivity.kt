package com.example.notastematicas.rest.quotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.notastematicas.databinding.ActivityJokeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JokeActivity : AppCompatActivity() {

    lateinit var binding: ActivityJokeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJokeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{siguienteBroma()}
    }


    fun siguienteBroma (){
        JokeRetrofitInstance.api.getJoke("/random_joke")
            .enqueue(object : Callback<JokeResponse> {
                override fun onResponse(
                    call: Call<JokeResponse>,
                    response: Response<JokeResponse>
                ) {
                    if (response.body() != null) {
                        binding.tvSetup.text = response.body()?.setup
                        binding.tvPunchline.text = response.body()?.punchline
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<JokeResponse>, t: Throwable) {
                    Log.d("TAG", t.message.toString())
                }
            })


    }
}