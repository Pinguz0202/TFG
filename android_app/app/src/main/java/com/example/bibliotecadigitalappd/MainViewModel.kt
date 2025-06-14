package com.example.bibliotecadigitalappd

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val welcomeText = "Hola, Biblioteca Digital desde ViewModel"
}