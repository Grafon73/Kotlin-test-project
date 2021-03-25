package com.kotlin.project.secondcontlinproject.controller

import com.kotlin.project.secondcontlinproject.service.PastureService
import com.kotlin.project.secondcontlinproject.view.PastureView
import com.kotlin.project.secondcontlinproject.view.PastureViewWithoutRabbit
import com.kotlin.project.secondcontlinproject.view.PastureViewWithoutRabbitAndQuality
import io.micrometer.core.instrument.config.validate.Validated.Valid
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import lombok.RequiredArgsConstructor
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@Api(value = "BookController", description = "Управление информацией о пастбищах")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = ["/pastures"], produces = [APPLICATION_JSON_VALUE])
class PastureController(private val pastureService: PastureService) {

    @PostMapping(path = ["/{rabbitId}"])
    @ApiOperation(value = "Добавить время выгула", httpMethod = "POST")
    fun addPasture(
        @PathVariable rabbitId: Int,
        @RequestBody  pasture: PastureViewWithoutRabbit
    ): PastureView {
        return pastureService.addPasture(rabbitId, pasture)
    }

    @PutMapping
    @ApiOperation(value = "Добавить пройденное растояние во время выгула", httpMethod = "PUT")
    fun addDistance(
        @RequestParam rabbitId: Int,
        @RequestBody  pasture: PastureViewWithoutRabbitAndQuality
    ): String {
        val pastureView = pastureService.addDistance(rabbitId, pasture)
        return ("В выбранный вами период, этот кролик гулял с "
                + pastureView.startDate + " до "
                + pastureView.endDate + " и ему успешно добавлено "
                + pasture.distance + " пройденного расстояния")
    }

    @GetMapping(path = ["/energyCounting/{rabbitId}"])
    @ApiOperation(value = "Получить количество затраченной кроликом энергии за период", httpMethod = "GET")
    fun energyCounting(
        @PathVariable rabbitId: Int,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) start: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) finish: LocalDateTime
    ): String {
        return ("За выбранный вами период, этот кролик затратил "
                + pastureService
            .summaryEnergyConsumption(rabbitId, start, finish)
                + "МДж энергии")
    }

    @GetMapping(path = ["/{rabbitId}"])
    @ApiOperation(value = "Получить все даты выгула определенного кролика", httpMethod = "GET")
    fun getAllPastures(@PathVariable rabbitId: Int): List<PastureView> {
        return pastureService.getAll(rabbitId)
    }
}
