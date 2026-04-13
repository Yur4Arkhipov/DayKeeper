package com.example.keepday.domain.repository

import com.example.keepday.domain.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface TaskRepository {
//    suspend fun updateTask(task: Task)
    fun observeDayData(date: Date): Flow<List<Task>>
    suspend fun insertTask(date: Date, task: Task)
    suspend fun deleteTask(taskId: Int)
}