package com.example.notastematicas.rest.quotes

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface JokeService {
    @GET
    fun getJoke(@Url url:String): Call<JokeResponse>
}