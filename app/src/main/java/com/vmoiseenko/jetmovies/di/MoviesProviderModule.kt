package com.vmoiseenko.jetmovies.di

import com.vmoiseenko.jetmovies.domain.repository.MoviesProviderRepository
import com.vmoiseenko.jetmovies.domain.repository.MoviesProviderRepositoryImpl
import com.vmoiseenko.jetmovies.domain.repository.TvShowsProviderRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesProviderModule {

    @Binds
    @Named("MoviesProvider")
    abstract fun bindMoviesProviderRepository(impl: MoviesProviderRepositoryImpl): MoviesProviderRepository

    @Binds
    @Named("TvShowsProvider")
    abstract fun bindTvShowsProviderRepository(impl: TvShowsProviderRepositoryImpl): MoviesProviderRepository
}
