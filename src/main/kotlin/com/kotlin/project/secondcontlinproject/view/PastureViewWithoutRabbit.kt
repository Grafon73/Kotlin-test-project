package com.kotlin.project.secondcontlinproject.view

import java.time.LocalDateTime


class PastureViewWithoutRabbit(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val quality: Boolean,
    val distance: Int
)