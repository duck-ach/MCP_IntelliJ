package com.example.demo.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandler {
    
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(e: NoSuchElementException): ResponseEntity<ErrorResponse> {
        logger.error("Resource not found: ${e.message}")
        val error = ErrorResponse("Resource not found", e.message ?: "The requested resource does not exist")
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }
    
//    @ExceptionHandler(NoResourceFoundException::class)
//    fun handleNoResourceFoundException(e: NoResourceFoundException): ResponseEntity<ErrorResponse> {
//        logger.error("Static resource not found: ${e.message}")
//        val error = ErrorResponse("Resource not found", "The requested static resource does not exist")
//        return ResponseEntity(error, HttpStatus.NOT_FOUND)
//    }
    
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Unhandled exception occurred", e)
        val error = ErrorResponse("Internal server error", "An unexpected error occurred")
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

data class ErrorResponse(
    val error: String,
    val message: String
)
