package com.example.keepday.data.usecase

import com.example.keepday.domain.model.Task
import com.example.keepday.domain.repository.TaskRepository
import com.example.keepday.domain.usecase.SaveTaskUseCase
import java.util.Date
import javax.inject.Inject

class SaveTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : SaveTaskUseCase {

    override suspend fun invoke(date: Date, task: Task) {
        taskRepository.insertTask(date, task)
    }
}