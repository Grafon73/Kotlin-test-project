package com.kotlin.project.secondcontlinproject.repo

import com.kotlin.project.secondcontlinproject.entity.Pasture
import org.springframework.data.jpa.repository.JpaRepository

interface PastureRepository:JpaRepository<Pasture, Int> {
    fun findAllByRabbitId(rabbitId: Int): List<Pasture>
}