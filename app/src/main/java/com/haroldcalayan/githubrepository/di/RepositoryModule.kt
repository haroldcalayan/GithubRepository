package com.haroldcalayan.githubrepository.di

import com.haroldcalayan.githubrepository.data.repository.GithubRepository
import com.haroldcalayan.githubrepository.data.repository.GithubRepositoryImpl
import com.haroldcalayan.githubrepository.data.source.local.database.AppDatabase
import com.haroldcalayan.githubrepository.data.source.remote.service.GithubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGithubRepository(database: AppDatabase, api: GithubApiService): GithubRepository {
        return GithubRepositoryImpl(database, api)
    }
}