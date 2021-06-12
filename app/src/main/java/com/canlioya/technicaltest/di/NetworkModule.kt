package com.canlioya.technicaltest.di

import com.canlioya.technicaltest.common.NetworkIdlingResource
import com.canlioya.technicaltest.data.network.AlbumApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitService() : AlbumApiService {
        val base_url = "https://jsonplaceholder.typicode.com/"

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(base_url)
            .build()
        return retrofit.create(AlbumApiService::class.java)
    }

    //This is only for instrumentation testing, it can be null on production code
    @Provides
    fun provideIdlingResource() : NetworkIdlingResource? = null
}