package com.example.bibliotecadigitalappd.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bibliotecadigitalappd.domain.repository.SampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SampleRepository
) : ViewModel() {

    val greeting = repository.getGreeting()
}