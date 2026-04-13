package com.example.keepday.domain.usecase

interface DeleteTaskUseCase {
    suspend operator fun invoke(taskId: Int)
}