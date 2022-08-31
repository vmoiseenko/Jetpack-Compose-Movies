package com.vmoiseenko.jetmovies.di

import com.vmoiseenko.jetmovies.domain.repository.MoviesRepository
import com.vmoiseenko.jetmovies.domain.repository.MoviesRepositoryImpl
import com.vmoiseenko.jetmovies.domain.repository.FavoriteRepository
import com.vmoiseenko.jetmovies.domain.repository.FavoriteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(impl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository
}
