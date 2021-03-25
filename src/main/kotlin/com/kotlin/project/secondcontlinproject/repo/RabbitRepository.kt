package com.kotlin.project.secondcontlinproject.repo

import com.kotlin.project.secondcontlinproject.entity.Rabbit
import org.springframework.data.jpa.repository.JpaRepository

interface RabbitRepository:JpaRepository<Rabbit, Int> {
}