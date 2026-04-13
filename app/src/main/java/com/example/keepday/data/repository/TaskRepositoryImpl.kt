package com.example.keepday.data.repository

import com.example.keepday.data.local.TaskDao
import com.example.keepday.data.local.TaskEntity
import com.example.keepday.data.local.toDomain
import com.example.keepday.domain.model.Task
import com.example.keepday.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
) : TaskRepository {

    private fun getDateKey(date: Date): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
    }

    override fun observeDayData(date: Date): Flow<List<Task>> {
        val dateKey = getDateKey(date)

        return taskDao.observeTasksForDate(dateKey)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun insertTask(
        date: Date,
        task: Task
    ) {
        val dateKey = getDateKey(date)
        taskDao.insertTask(
            TaskEntity(
                name = task.name,
                dateStart = task.dateStart,
                dateFinish = task.dateFinish,
                description = task.description,
                date = dateKey,
            )
        )
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTaskById(taskId)
    }

//    override suspend fun getTaskById(id: Int): Task? =
//        taskDao.getTaskById(id)?.toDomain()
//

//    override suspend fun updateTask(task: Task) =
//        taskDao.updateTask(task.toEntity())
}

