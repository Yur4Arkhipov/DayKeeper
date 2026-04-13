package com.example.keepday.core.di

import com.example.keepday.data.repository.SelectedDateHolderImpl
import com.example.keepday.data.repository.TaskRepositoryImpl
import com.example.keepday.domain.repository.SelectedDateHolder
import com.example.keepday.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    fun bindSelectedDateHolder(
        impl: SelectedDateHolderImpl
    ): SelectedDateHolder
}