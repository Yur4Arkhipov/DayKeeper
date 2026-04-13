package com.example.keepday.domain.usecase

import com.example.keepday.domain.model.Task
import java.util.Date

interface SaveTaskUseCase {
    suspend operator fun invoke(date: Date, task: Task)
}