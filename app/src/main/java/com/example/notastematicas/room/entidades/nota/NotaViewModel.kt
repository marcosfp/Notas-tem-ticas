package com.example.notastematicas.room.entidades.nota

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class NotaViewModel(private val repositorioNotas: NotasRepositorio) : ViewModel() {

    //val todasLasNotas: LiveData<List<Nota>> = repositorioNotas.todasLasNotas.asLiveData()

    fun insertNota(nota: Nota) = viewModelScope.launch {
        repositorioNotas.insertarNota(nota)
    }
}


class NotaViewModelFactory(private val repositorioNotas: NotasRepositorio) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotaViewModel(repositorioNotas) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}