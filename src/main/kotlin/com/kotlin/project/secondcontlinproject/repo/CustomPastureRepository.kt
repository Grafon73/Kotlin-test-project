package com.kotlin.project.secondcontlinproject.repo

import com.kotlin.project.secondcontlinproject.entity.Pasture
import java.time.LocalDateTime


interface CustomPastureRepository {
    fun findAllByRabbitIdStartDateEndDate(
        rabbitId: Int,
        start: LocalDateTime,
        finish: LocalDateTime
    ): List<Pasture>
}
