package com.example.film_app.di.module

import com.example.film_app.model.repository.detailRepo.DetailRepository
import com.example.film_app.model.repository.detailRepo.DetailRepositoryImpl
import com.example.film_app.model.repository.homeRepo.HomeRepository
import com.example.film_app.model.repository.homeRepo.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {


    @Binds
    abstract fun homeRepository(mainRepository: HomeRepositoryImpl) : HomeRepository

    @Binds
    abstract fun detailRepository(mainRepository: DetailRepositoryImpl) : DetailRepository


}