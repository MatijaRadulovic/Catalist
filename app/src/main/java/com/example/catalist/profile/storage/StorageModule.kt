package com.example.catalist.profile.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    @Singleton
    fun provideProfileDataStore(
        @ApplicationContext context: Context,
    ): DataStore<ProfileModel?> {
        return DataStoreFactory.create(
            storage = OkioStorage(
                fileSystem = FileSystem.SYSTEM,
                serializer = ProfileSerializer(),
                producePath = {
                    context.dataStoreFile("profile.json").toOkioPath()
                },
            )
        )
    }
}
