package com.example.employee_api.model.dto

import com.example.employee_api.model.entities.EmployeeEntity

data class EmployeeResponse(
    val id: Long,
    val name: String,
    val age: Int,
    val salary: Int,
    val profileImg: String?
) {
    companion object {
        fun fromEntity(entity: EmployeeEntity): EmployeeResponse {
            return EmployeeResponse(
                id = entity.id,
                name = entity.employeeName,
                age = entity.employeeAge,
                salary = entity.employeeSalary,
                profileImg = entity.profileImage
            )
        }
    }
}