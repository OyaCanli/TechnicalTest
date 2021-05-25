package com.canlioya.technicaltest.di

import com.canlioya.technicaltest.data.IRepository
import com.canlioya.technicaltest.data.Repository
import com.canlioya.technicaltest.data.network.AlbumApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(retrofitService : AlbumApiService) : IRepository = Repository(retrofitService)

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

    @IODispatcher
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}