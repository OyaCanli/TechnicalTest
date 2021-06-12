package com.canlioya.technicaltest.di

import com.canlioya.technicaltest.data.IRepository
import com.canlioya.technicaltest.data.Repository
import com.canlioya.technicaltest.data.network.AlbumApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(retrofitService : AlbumApiService) : IRepository = Repository(retrofitService)

    @IODispatcher
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}