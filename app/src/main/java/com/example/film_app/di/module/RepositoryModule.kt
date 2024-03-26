package com.example.film_app.di.module

import com.example.film_app.model.repository.MainRepository
import com.example.film_app.model.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {


    @Binds
    abstract fun repository(mainRepository: MainRepositoryImpl) : MainRepository


}