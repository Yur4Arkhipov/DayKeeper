package com.example.keepday.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.ZoneId

@Module
@InstallIn(SingletonComponent::class)
object TimeModule {

    @Provides
    fun provideZoneId(): ZoneId {
        return ZoneId.systemDefault()
    }
}