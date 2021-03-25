package com.kotlin.project.secondcontlinproject.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*


@ControllerAdvice
class MyExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(DataNotFoundException::class)
    fun handleDataNotFoundException(
        ex: DataNotFoundException
    ): ResponseEntity<Any> {
        val body: MutableMap<String, Any?> = LinkedHashMap()
        body["error"] = ex.message
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(
        ex: CustomException
    ): ResponseEntity<Any> {
        val body: MutableMap<String, Any?> = LinkedHashMap()
        body["error"] = ex.message
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }
}
