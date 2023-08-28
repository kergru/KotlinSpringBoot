package de.kergru.demo.itemapi.rest

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler
    fun handleItemNotFoundException(e: NoSuchElementException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        problemDetail.detail = e.message
        problemDetail.title = "Item not found"
        return ResponseEntity(problemDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    fun handleConstraintException(e: ConstraintViolationException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        problemDetail.detail = e.message
        problemDetail.title = "Constraint Violation"
        return ResponseEntity(problemDetail, HttpStatus.BAD_REQUEST);
    }
}