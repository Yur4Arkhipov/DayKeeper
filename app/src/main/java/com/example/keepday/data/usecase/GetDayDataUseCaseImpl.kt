package com.example.keepday.data.usecase

import com.example.keepday.domain.model.Task
import com.example.keepday.domain.repository.TaskRepository
import com.example.keepday.domain.usecase.GetDayDataUseCase
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class GetDayDataUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : GetDayDataUseCase {

    override fun invoke(date: Date): Flow<List<Task>> {
        return taskRepository.observeDayData(date)
    }
}