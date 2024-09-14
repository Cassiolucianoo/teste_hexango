package com.example.hexagon.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

object DateUtils {
    fun calculateAge(birthDate: String): Int? {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return try {
            val birthLocalDate = LocalDate.parse(birthDate, formatter)
            val currentDate = LocalDate.now()
            ChronoUnit.YEARS.between(birthLocalDate, currentDate).toInt()
        } catch (e: DateTimeParseException) {
            null
        }
    }
}
