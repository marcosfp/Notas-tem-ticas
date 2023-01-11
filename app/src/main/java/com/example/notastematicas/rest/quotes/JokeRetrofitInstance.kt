package com.example.notastematicas.rest.quotes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JokeRetrofitInstance {
    val api : JokeService by lazy {
        Retrofit.Builder()
            .baseUrl("https://official-joke-api.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JokeService::class.java)
    }

}
