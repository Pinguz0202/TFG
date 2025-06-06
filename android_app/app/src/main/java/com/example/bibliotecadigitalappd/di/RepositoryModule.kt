package com.example.bibliotecadigitalappd.di

import com.example.bibliotecadigitalappd.domain.repository.SampleRepository
import com.example.bibliotecadigitalappd.data.repository.SampleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSampleRepository(
        impl: SampleRepositoryImpl
    ): SampleRepository
}