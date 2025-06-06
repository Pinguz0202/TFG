package com.example.bibliotecadigitalappd.data.repository

import com.example.bibliotecadigitalappd.domain.repository.SampleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleRepositoryImpl @Inject constructor() : SampleRepository {
    override fun getGreeting(): String = "¡Hola desde el repositorio!"
}