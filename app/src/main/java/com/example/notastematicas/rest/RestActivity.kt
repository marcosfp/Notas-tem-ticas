package com.example.notastematicas.rest

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notastematicas.databinding.ActivityRestBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RestActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    lateinit var imagesPuppies: List<String>
    private lateinit var viewModel: DogsViewModel
    private lateinit var dogAdapter: DogsAdapter

    private lateinit var binding: ActivityRestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBreed.setOnQueryTextListener(this)

        prepareRecyclerView()
        viewModel = ViewModelProvider(this)[DogsViewModel::class.java]
        viewModel.observeMovieLiveData().observe(this, Observer { dogList ->
            dogAdapter.setDogList(dogList)
            dogAdapter.notifyDataSetChanged()
        })
    }

    private fun prepareRecyclerView() {
        dogAdapter = DogsAdapter()
        binding.rvDogs.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = dogAdapter
        }
    }

    private fun initCharacter(puppies: DogsResponse) {

        if (puppies.status == "success") {
            imagesPuppies = puppies.images
        }
        binding.rvDogs.setHasFixedSize(true)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = dogAdapter
    }


    override fun onQueryTextSubmit(query: String): Boolean {
        searchByName(query.lowercase())
        return true
    }

    private fun searchByName(query: String) {
            viewModel.getDog(query)
    }

    private fun showErrorDialog() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}

