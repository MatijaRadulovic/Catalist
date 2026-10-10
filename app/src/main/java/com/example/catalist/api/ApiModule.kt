package com.example.catalist.api

import com.example.catalist.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
        val updatedRequest = it.request().newBuilder()
            .addHeader("x-api-key", BuildConfig.CAT_API_KEY).build()
            it.proceed(updatedRequest)}
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
                redactHeader("x-api-key")
            }
        )
        .build()


    @Singleton
    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .client(okHttpClient)
            .addConverterFactory(CatsJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }


    @Provides
    @Singleton
    fun provideCatsApi(retrofit: Retrofit) = retrofit.create<CatsApi>()



}