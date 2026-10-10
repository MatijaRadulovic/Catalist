package com.example.catalist.repos

import com.example.catalist.api.CatsApi
import com.example.catalist.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Singleton
    @Provides
    fun provideRepo(catsApi: CatsApi, database: AppDatabase): CatRepoInterface= CatApiRepoImpl(
        catsApi =catsApi,
        catsDb = database
    )
}