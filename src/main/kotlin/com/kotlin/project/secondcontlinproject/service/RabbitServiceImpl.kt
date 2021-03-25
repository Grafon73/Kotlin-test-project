package com.kotlin.project.secondcontlinproject.service

import com.kotlin.project.secondcontlinproject.entity.Rabbit
import com.kotlin.project.secondcontlinproject.exception.CustomException
import com.kotlin.project.secondcontlinproject.exception.DataNotFoundException
import com.kotlin.project.secondcontlinproject.repo.RabbitRepository
import com.kotlin.project.secondcontlinproject.view.RabbitView
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import kotlin.streams.toList


@Service
class RabbitServiceImpl(private val rabbitRepository:RabbitRepository) : RabbitService {

    override fun add(name: String, color: String, age: Int, file: MultipartFile?): RabbitView {
        val rabbitEntity = Rabbit(name,color,age,photoToByteArray(file))
        rabbitRepository.save(rabbitEntity)
         return rabbitEntity.toDto()
    }

    override fun remove(id: Int) {
        val rabbit = rabbitRepository
            .findById(id)
            .orElseThrow { DataNotFoundException("Кролик с данным ID отсутствует в базе") }
        rabbitRepository.delete(rabbit)

    }

    override fun edit(id: Int, name: String, color: String, age: Int, file: MultipartFile?): RabbitView {
        val rabbitEntity = rabbitRepository
            .findById(id)
            .orElseThrow { DataNotFoundException("Кролик с данным ID отсутствует в базе") }
        rabbitEntity.age = age
        rabbitEntity.name = name
        rabbitEntity.color = color
        if (file != null) rabbitEntity.photo = photoToByteArray(file)
        rabbitRepository.save(rabbitEntity)
        return rabbitEntity.toDto()

    }

    override fun getAll(): List<RabbitView> {
        return rabbitRepository.findAll().stream().map { rabbit -> rabbit.toDto() }.toList()

    }

    private fun photoToByteArray(file: MultipartFile?): ByteArray {
        if (file == null) {
            return ByteArray(0)
        }
        return try {
            file.bytes
        } catch (e: IOException) {
            throw CustomException("Неверный формат фотографии")
        }
    }
    fun Rabbit.toDto() = RabbitView(
        name = name,
        age = age,
        color = color,
        photo = photo
    )
}
