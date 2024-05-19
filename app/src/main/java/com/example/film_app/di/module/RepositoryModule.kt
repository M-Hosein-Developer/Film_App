package com.example.film_app.di.module

import com.example.film_app.model.repository.detailRepo.DetailAndWatchListRepository
import com.example.film_app.model.repository.detailRepo.DetailAndWatchListRepositoryImpl
import com.example.film_app.model.repository.filmCoverRepo.FilmCoverRepository
import com.example.film_app.model.repository.filmCoverRepo.FilmCoverRepositoryImpl
import com.example.film_app.model.repository.homeRepo.HomeRepository
import com.example.film_app.model.repository.homeRepo.HomeRepositoryImpl
import com.example.film_app.model.repository.searchRepo.SearchRepository
import com.example.film_app.model.repository.searchRepo.SearchRepositoryImpl
import com.example.film_app.model.repository.themeRepo.ThemeRepository
import com.example.film_app.model.repository.themeRepo.ThemeRepositoryImpl
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
    abstract fun detailRepository(mainRepository: DetailAndWatchListRepositoryImpl) : DetailAndWatchListRepository

    @Binds
    abstract fun searchRepository(mainRepository: SearchRepositoryImpl) : SearchRepository

    @Binds
    abstract fun themeRepository(mainRepository: ThemeRepositoryImpl) : ThemeRepository

    @Binds
    abstract fun filmCoverRepository(mainRepository: FilmCoverRepositoryImpl) : FilmCoverRepository

}