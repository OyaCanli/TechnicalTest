package com.canlioya.technicaltest.di

import com.canlioya.technicaltest.data.IRepository
import com.canlioya.technicaltest.data.Repository
import com.canlioya.technicaltest.data.network.ApiProvider
import com.canlioya.technicaltest.data.network.IApiProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiProvider() : IApiProvider = ApiProvider

    @Singleton
    @Provides
    fun provideRepository(apiProvider: IApiProvider) : IRepository = Repository(apiProvider)

}