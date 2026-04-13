package com.example.keepday.data.usecase

import com.example.keepday.domain.repository.TaskRepository
import com.example.keepday.domain.usecase.DeleteTaskUseCase
import javax.inject.Inject

class DeleteTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : DeleteTaskUseCase {

    override suspend fun invoke(taskId: Int) {
        taskRepository.deleteTask(taskId)
    }
}