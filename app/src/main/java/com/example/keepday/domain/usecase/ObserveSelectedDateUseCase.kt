package com.example.keepday.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import java.util.Date

interface ObserveSelectedDateUseCase  {
    operator fun invoke() : StateFlow<Date>
}