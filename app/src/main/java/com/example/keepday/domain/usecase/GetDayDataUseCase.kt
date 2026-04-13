package com.example.keepday.domain.usecase

import com.example.keepday.domain.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface GetDayDataUseCase {
    operator fun invoke(date: Date): Flow<List<Task>>
}