package com.kotlin.project.secondcontlinproject.service

import com.kotlin.project.secondcontlinproject.entity.Pasture
import com.kotlin.project.secondcontlinproject.entity.Rabbit
import com.kotlin.project.secondcontlinproject.exception.CustomException
import com.kotlin.project.secondcontlinproject.exception.DataNotFoundException
import com.kotlin.project.secondcontlinproject.repo.CustomPastureRepository
import com.kotlin.project.secondcontlinproject.repo.PastureRepository
import com.kotlin.project.secondcontlinproject.repo.RabbitRepository
import com.kotlin.project.secondcontlinproject.view.PastureView
import com.kotlin.project.secondcontlinproject.view.PastureViewWithoutRabbit
import com.kotlin.project.secondcontlinproject.view.PastureViewWithoutRabbitAndQuality
import com.kotlin.project.secondcontlinproject.view.RabbitViewWithoutPhoto
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import kotlin.streams.toList


@Service
class PastureServiceImpl(
    private val rabbitRepository: RabbitRepository,
    private val pastureRepository: PastureRepository,
    private val customPastureRepository: CustomPastureRepository
) : PastureService {

    override fun addPasture(rabbitId: Int, pasture: PastureViewWithoutRabbit): PastureView {
        val rabbit = rabbitRepository.findById(rabbitId).orElseThrow {
            DataNotFoundException(
                "Кролик с данным ID отсутствует в базе"
            )
        }
        if (pasture.endDate.isBefore(pasture.startDate))
            throw CustomException("Неверно заданы даты пастьбы")

        val hoursOfPasture: Long = Duration
            .between(pasture.startDate, pasture.endDate)
            .toHours()
        val pastureEntity = Pasture(
            pasture.startDate,
            pasture.endDate,
            pasture.quality,
            pasture.distance,
            EnergyConsumption().energyConsumptionCountingOnWalk(rabbit, hoursOfPasture),
            rabbit)
        rabbit.addPasture(pastureEntity)
        rabbitRepository.save(rabbit)
        return pastureEntity.toDto()
    }

    override fun addDistance(
        rabbitId: Int,
        pasture: PastureViewWithoutRabbitAndQuality
    ): PastureView {
        rabbitRepository.findById(rabbitId).orElseThrow {
            DataNotFoundException(
                "Кролик с данным ID отсутствует в базе"
            )
        }
        if (pasture.endDate.isBefore(pasture.startDate))
            throw CustomException("Неверно заданы даты пастьбы")

        val pastureList: List<Pasture> = customPastureRepository.findAllByRabbitIdStartDateEndDate(
            rabbitId,
            pasture.startDate,
            pasture.endDate
        )
        if (pastureList.isEmpty()) {
            throw CustomException("В данный период, этот кролик не выгуливался")
        } else if (pastureList.size > 1) {
            throw CustomException(
                "В данный период, количество выгулов этого кролика: "
                        + pastureList.size
                        + ".Пожалуйста, уменьшите выбранный период времени"
            )
        }
        val pastureEntity = pastureList[0]
        pastureEntity.distance = pastureEntity.distance + pasture.distance
        pastureRepository.save(pastureEntity)
        return pastureEntity.toDto()
    }

   override fun summaryEnergyConsumption(
        rabbitId: Int,
        start: LocalDateTime,
        finish: LocalDateTime
    ): Double {
        val rabbit = rabbitRepository.findById(rabbitId).orElseThrow {
            DataNotFoundException(
                "Кролик с данным ID отсутствует в базе"
            )
        }
       if (finish.isBefore(start))
            throw CustomException("Неверно заданы даты пастьбы")

        val pastureList: List<Pasture> = customPastureRepository.findAllByRabbitIdStartDateEndDate(
            rabbitId,
            start,
            finish
        )
        var hoursOfPasture = 0L
        var summaryEnergyConsumption = 0.0
        for (p: Pasture in pastureList) {
            hoursOfPasture += Duration.between(p.startDate, p.endDate).toHours()
            summaryEnergyConsumption += p.energyConsumption
        }
        val hoursOfRest: Long = Duration.between(start, finish).toHours() - hoursOfPasture
        val energyConsumption = EnergyConsumption()
        summaryEnergyConsumption += energyConsumption
            .energyConsumptionCountingOnRest(rabbit, hoursOfRest)
        return summaryEnergyConsumption
    }

   override fun getAll(rabbitId: Int): List<PastureView> {
        return pastureRepository.findAllByRabbitId(rabbitId).stream().map { pasture -> pasture.toDto() }.toList()
    }

    fun Pasture.toDto()=PastureView(
        startDate = startDate,
        endDate = endDate,
        energyConsumption = energyConsumption,
        distance = distance,
        quality = quality,
        rabbit = rabbit!!.toDto()
    )

    fun Rabbit.toDto() = RabbitViewWithoutPhoto(
        name = name,
        age = age,
        color = color
    )
}
