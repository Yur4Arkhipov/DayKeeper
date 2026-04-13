package com.example.keepday.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.keepday.domain.model.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String,
    val date: String,
)

fun TaskEntity.toDomain(): Task {
    return Task(
        id = id,
        dateStart = dateStart,
        dateFinish = dateFinish,
        name = name,
        description = description
    )
}

fun Task.toEntity(date: String): TaskEntity {
    return TaskEntity(
        id = id,
        dateStart = dateStart,
        dateFinish = dateFinish,
        name = name,
        description = description,
        date = date
    )
}