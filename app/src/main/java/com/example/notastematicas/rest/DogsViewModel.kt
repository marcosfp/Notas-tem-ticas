package com.example.notastematicas.rest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogsViewModel : ViewModel() {
    private var dogsLiveData = MutableLiveData<DogsResponse>()


    fun getDog(query: String) {
        RetrofitInstance.api.getCharacterByName("$query/images")
            .enqueue(object : Callback<DogsResponse> {
                override fun onResponse(
                    call: Call<DogsResponse>,
                    response: Response<DogsResponse>
                ) {
                    if (response.body() != null) {
                        dogsLiveData.value = response.body()
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<DogsResponse>, t: Throwable) {
                    Log.d("TAG", t.message.toString())
                }
            })
    }

    fun observeMovieLiveData(): LiveData<DogsResponse> {
        return dogsLiveData
    }
}