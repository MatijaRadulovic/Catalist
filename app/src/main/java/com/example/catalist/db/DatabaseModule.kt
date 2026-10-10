package com.example.catalist.db

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context,): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rma.db",
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

}