package com.example.keepday.core.di

import com.example.keepday.data.usecase.DeleteTaskUseCaseImpl
import com.example.keepday.data.usecase.GetDayDataUseCaseImpl
import com.example.keepday.data.usecase.ObserveSelectedDateUseCaseImpl
import com.example.keepday.data.usecase.SaveTaskUseCaseImpl
import com.example.keepday.data.usecase.SetSelectedDateUseCaseImpl
import com.example.keepday.domain.repository.SelectedDateHolder
import com.example.keepday.domain.repository.TaskRepository
import com.example.keepday.domain.usecase.DeleteTaskUseCase
import com.example.keepday.domain.usecase.GetDayDataUseCase
import com.example.keepday.domain.usecase.ObserveSelectedDateUseCase
import com.example.keepday.domain.usecase.SaveTaskUseCase
import com.example.keepday.domain.usecase.SetSelectedDateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetDayDataUseCase(taskRepository: TaskRepository) : GetDayDataUseCase {
        return GetDayDataUseCaseImpl(taskRepository)
    }

    @Provides
    fun provideObserveDayDataUseCase(selectedDateRepository: SelectedDateHolder) : ObserveSelectedDateUseCase {
        return ObserveSelectedDateUseCaseImpl(selectedDateRepository)
    }

    @Provides
    fun setSelectedDateUseCase(selectedDateRepository: SelectedDateHolder) : SetSelectedDateUseCase {
        return SetSelectedDateUseCaseImpl(selectedDateRepository)
    }

    @Provides
    fun provideSaveTaskUseCase(taskRepository: TaskRepository): SaveTaskUseCase {
        return SaveTaskUseCaseImpl(taskRepository)
    }

    @Provides
    fun provideDeleteTaskUseCase(taskRepository: TaskRepository): DeleteTaskUseCase {
        return DeleteTaskUseCaseImpl(taskRepository)
    }
}