package com.example.bibliotecadigitalappd.presentation.screens.crud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotecadigitalappd.data.remote.dtos.LibroDto
import com.example.bibliotecadigitalappd.data.repository.LibroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibroViewModel @Inject constructor(
    private val repository: LibroRepository
): ViewModel() {

    private val _libros = MutableStateFlow<List<LibroDto>>(emptyList())
    val libros: StateFlow<List<LibroDto>> = _libros

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarLibros() {
        viewModelScope.launch {
            try {
                val response = repository.getLibros()
                if (response.isSuccessful) {
                    _libros.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "Error al cargar libros: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar libros: ${e.message}"
            }
        }
    }

    fun crearLibro(libro: LibroDto, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.crearLibro(libro)
                if (response.isSuccessful) {
                    cargarLibros()
                    onSuccess()
                    _error.value = null
                } else {
                    _error.value = "Error al crear libro: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error al crear libro: ${e.message}"
            }
        }
    }

    fun editarLibro(isbn: String, libro: LibroDto, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.editarLibro(isbn, libro)
                if (response.isSuccessful) {
                    cargarLibros()
                    onSuccess()
                    _error.value = null
                } else {
                    _error.value = "Error al editar libro: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error al editar libro: ${e.message}"
            }
        }
    }

    fun eliminarLibro(isbn: String) {
        viewModelScope.launch {
            try {
                val response = repository.eliminarLibro(isbn)
                if (response.isSuccessful) {
                    cargarLibros()
                    _error.value = null
                } else {
                    _error.value = "Error al eliminar libro: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error al eliminar libro: ${e.message}"
            }
        }
    }
}