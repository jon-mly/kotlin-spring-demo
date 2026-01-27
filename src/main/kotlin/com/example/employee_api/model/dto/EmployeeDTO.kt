package com.example.employee_api.model.dto

data class EmployeeDTO(
    val id: Int,
    val name: String,
    val age: Int,
    val salary: Int,
    val profileImg: String?
)