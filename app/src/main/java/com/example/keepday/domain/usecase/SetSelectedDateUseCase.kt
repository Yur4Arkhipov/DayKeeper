package com.example.keepday.domain.usecase

import java.util.Date

interface SetSelectedDateUseCase  {
    suspend operator fun invoke(date: Date)
}