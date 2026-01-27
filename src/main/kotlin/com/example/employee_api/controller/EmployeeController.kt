package com.example.employee_api.controller

import com.example.employee_api.model.dto.ApiResponse
import com.example.employee_api.model.dto.ApiResponseStatus
import com.example.employee_api.model.dto.EmployeeDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class EmployeeController {

    companion object {
        val mockEmployees: List<EmployeeDTO> = listOf(
            EmployeeDTO(id = 1, name = "First employee", age = 30, salary = 15000, profileImg = null),
            EmployeeDTO(id = 2, name = "Second employee", age = 35, salary = 56000, profileImg = null),
            EmployeeDTO(id = 3, name = "Third employee", age = 62, salary = 999999, profileImg = null),
        )
    }

    @GetMapping("/employees")
    fun getAllEmployees(): ApiResponse<List<EmployeeDTO>> {
        return ApiResponse<List<EmployeeDTO>>(
            status = ApiResponseStatus.SUCCESS,
            data = mockEmployees,
            message = null
        )
    }

    @GetMapping("/employees/{id}")
    fun getEmployeeById(@PathVariable id: String): ApiResponse<EmployeeDTO?> {
        val employee: EmployeeDTO? = mockEmployees.find { it.id.toString() == id }
        val response = employee?.let {
            ApiResponse<EmployeeDTO?>(
                status = ApiResponseStatus.SUCCESS,
                data = it,
                message = null
            )
        } ?: ApiResponse<EmployeeDTO?>(status = ApiResponseStatus.FAILURE, data = null, message = null)
        return response
    }

}
