package com.kotlin.project.secondcontlinproject.controller

import com.kotlin.project.secondcontlinproject.service.RabbitService
import com.kotlin.project.secondcontlinproject.view.RabbitView
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import lombok.RequiredArgsConstructor
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@Api(value = "BookController", description = "Управление информацией о кроликах")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ["/rabbits"], produces = [APPLICATION_JSON_VALUE])
class RabbitController(private val rabbitService: RabbitService) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ApiOperation(value = "Добавить кролика", httpMethod = "POST", consumes = "multipart/form-data")
    fun addRabbit(
        @RequestParam name: String,
        @RequestParam color: String,
        @RequestParam age: Int,
        @RequestPart(required = false, value = "file") photo: MultipartFile?
    ): RabbitView? {
        return rabbitService.add(name, color, age, photo)
    }

    @DeleteMapping(path = ["/{id}"])
    @ApiOperation(value = "Удалить кролика", httpMethod = "DELETE")
    fun removeRabbit(@PathVariable id: Int): String {
        rabbitService.remove(id)
        return "Кролик с ID $id успешно удален"
    }

    @PostMapping(path = ["/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ApiOperation(value = "Изменить данные кролика по ID", httpMethod = "POST", consumes = "multipart/form-data")
    fun editRabbit(
        @PathVariable id: Int,
        @RequestParam name: String,
        @RequestParam color: String,
        @RequestParam age: Int,
        @RequestPart(required = false, value = "file") photo: MultipartFile?
    ): RabbitView {
        return rabbitService.edit(id, name, color, age, photo)
    }

    @GetMapping
    @ApiOperation(value = "Получить список всех кроликов", httpMethod = "GET")
    fun getAllRabbits(): List<RabbitView> {
        return rabbitService.getAll()
    }

}
