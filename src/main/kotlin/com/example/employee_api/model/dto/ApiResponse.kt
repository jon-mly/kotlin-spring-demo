package com.example.employee_api.model.dto

import com.fasterxml.jackson.annotation.JsonValue

enum class ApiResponseStatus(@JsonValue val value: String) {
    SUCCESS("success"),
    FAILURE("failure")
}

data class ApiResponse<T>(val status: ApiResponseStatus, val data: T?, val message: String?)
