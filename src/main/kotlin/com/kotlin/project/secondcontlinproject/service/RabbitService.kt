package com.kotlin.project.secondcontlinproject.service

import com.kotlin.project.secondcontlinproject.view.RabbitView
import org.springframework.web.multipart.MultipartFile

interface RabbitService {
    fun add(name: String, color: String, age: Int, file: MultipartFile?): RabbitView
    fun remove(id: Int)
    fun edit(id: Int, name: String, color: String, age: Int, file: MultipartFile?): RabbitView
    fun getAll(): List<RabbitView>
}