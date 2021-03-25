package com.kotlin.project.secondcontlinproject.service

import com.kotlin.project.secondcontlinproject.entity.Rabbit
import com.kotlin.project.secondcontlinproject.exception.CustomException


class EnergyConsumption {
    private val AGE_6_MONTH = 6
    private val AGE_12_MONTH = 12
    private val AGE_20_MONTH = 20
    private val AGE_40_MONTH = 40
    private val AGE_50_MONTH = 50
    fun energyConsumptionCountingOnWalk(rabbit: Rabbit, hours: Long): Double {
        val age = rabbit.age
        val averageWeight: Double
        val averageEnergyConsumptionPerKg: Double
        when {
            age < 0 -> {
                throw CustomException("Неверный возраст кролика")
            }
            age <= AGE_6_MONTH -> {
                averageWeight = 0.8
                averageEnergyConsumptionPerKg = 0.20
            }
            age <= AGE_12_MONTH -> {
                averageWeight = 1.5
                averageEnergyConsumptionPerKg = 0.35
            }
            age <= AGE_20_MONTH -> {
                averageWeight = 2.0
                averageEnergyConsumptionPerKg = 0.5
            }
            age <= AGE_40_MONTH -> {
                averageWeight = 2.6
                averageEnergyConsumptionPerKg = 1.84
            }
            age <= AGE_50_MONTH -> {
                averageWeight = 3.5
                averageEnergyConsumptionPerKg = 2.3
            }
            else -> {
                averageWeight = 5.0
                averageEnergyConsumptionPerKg = 2.73
            }
        }
        return averageEnergyConsumptionPerKg * averageWeight / 24 * hours
    }

    fun energyConsumptionCountingOnRest(rabbit: Rabbit, hours: Long): Double {
        val age = rabbit.age
        val averageWeight: Double
        val averageEnergyConsumptionPerKg: Double
        when {
            age < 0 -> {
                throw CustomException("Неверный возраст кролика")
            }
            age <= AGE_6_MONTH -> {
                averageWeight = 0.8
                averageEnergyConsumptionPerKg = 0.10
            }
            age <= AGE_12_MONTH -> {
                averageWeight = 1.5
                averageEnergyConsumptionPerKg = 0.25
            }
            age <= AGE_20_MONTH -> {
                averageWeight = 2.0
                averageEnergyConsumptionPerKg = 0.4
            }
            age <= AGE_40_MONTH -> {
                averageWeight = 2.6
                averageEnergyConsumptionPerKg = 1.34
            }
            age <= AGE_50_MONTH -> {
                averageWeight = 3.5
                averageEnergyConsumptionPerKg = 1.8
            }
            else -> {
                averageWeight = 5.0
                averageEnergyConsumptionPerKg = 2.2
            }
        }
        return averageEnergyConsumptionPerKg * averageWeight / 24 * hours
    }

}