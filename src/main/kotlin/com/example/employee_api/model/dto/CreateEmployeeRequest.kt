package com.example.employee_api.model.dto

data class CreateEmployeeRequest(
    val name: String, val age: Int, val salary: Int, val profileImage: String?
)
