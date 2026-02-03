package com.example.employee_api.model.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class CreateEmployeeRequest(
    @field:NotBlank
    val name: String,

    @field:Min(18, message = "Must be at least 18")
    @field:Max(100, message = "Age exceeds maximum")
    val age: Int,

    @field:Min(0, message = "Salary must be positive")
    @field:Max(10000000, message = "Salary exceeds maximum")
    val salary: Int,

    val profileImage: String?
)
