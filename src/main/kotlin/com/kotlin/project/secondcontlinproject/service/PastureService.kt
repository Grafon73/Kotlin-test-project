package com.kotlin.project.secondcontlinproject.service

import com.kotlin.project.secondcontlinproject.view.PastureView
import com.kotlin.project.secondcontlinproject.view.PastureViewWithoutRabbit
import com.kotlin.project.secondcontlinproject.view.PastureViewWithoutRabbitAndQuality
import java.time.LocalDateTime


interface PastureService {
    fun addPasture(
        rabbitId: Int,
        pasture: PastureViewWithoutRabbit
    ): PastureView

    fun addDistance(
        rabbitId: Int,
        pasture: PastureViewWithoutRabbitAndQuality
    ): PastureView

    fun summaryEnergyConsumption(
        rabbitId: Int,
        start: LocalDateTime,
        finish: LocalDateTime
    ): Double

    fun getAll(rabbitId: Int): List<PastureView>
}
