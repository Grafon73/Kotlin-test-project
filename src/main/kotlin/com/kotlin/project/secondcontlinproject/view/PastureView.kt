package com.kotlin.project.secondcontlinproject.view

import java.time.LocalDateTime

class PastureView(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val quality: Boolean,
    val distance: Int,
    val energyConsumption: Double,
    val rabbit:RabbitViewWithoutPhoto
)