package com.example.employee_api.controller

import com.example.employee_api.model.dto.ApiResponse
import com.example.employee_api.model.dto.ApiResponseStatus
import com.example.employee_api.model.dto.CreateEmployeeRequest
import com.example.employee_api.model.dto.EmployeeResponse
import com.example.employee_api.model.dto.UpdateEmployeeRequest
import com.example.employee_api.service.EmployeeService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class EmployeeController(private val employeeService: EmployeeService) {

    companion object {
        val mockEmployees: List<EmployeeResponse> = listOf(
            EmployeeResponse(id = 1, name = "First employee", age = 30, salary = 15000, profileImg = null),
            EmployeeResponse(id = 2, name = "Second employee", age = 35, salary = 56000, profileImg = null),
            EmployeeResponse(id = 3, name = "Third employee", age = 62, salary = 999999, profileImg = null),
        )
    }

    @GetMapping("/employees")
    fun getAllEmployees(): ApiResponse<List<EmployeeResponse>> {
        val employees: List<EmployeeResponse> = employeeService.getAllEmployees()
        return ApiResponse<List<EmployeeResponse>>(
            status = ApiResponseStatus.SUCCESS,
            data = employees,
            message = null
        )
    }

    @GetMapping("/employees/{id}")
    fun getEmployeeById(@PathVariable id: Long): ApiResponse<EmployeeResponse?> {
        val employee: EmployeeResponse? = employeeService.getEmployeeById(id)
        val response = employee?.let {
            ApiResponse<EmployeeResponse?>(
                status = ApiResponseStatus.SUCCESS,
                data = it,
                message = null
            )
        } ?: ApiResponse<EmployeeResponse?>(status = ApiResponseStatus.FAILURE, data = null, message = null)
        return response
    }

    @PostMapping("/employees")
    fun createEmployee(@RequestBody request: CreateEmployeeRequest): ApiResponse<EmployeeResponse> {
        // TODO: parse from request body
        val createEmployee: EmployeeResponse = employeeService.createEmployee(request)
        return ApiResponse<EmployeeResponse>(
            status = ApiResponseStatus.SUCCESS,
            data = createEmployee,
            message = null
        )
    }

    @PutMapping("/employees/{id}")
    fun updateEmployee(
        @PathVariable id: Long,
        @RequestBody request: UpdateEmployeeRequest
    ): ApiResponse<EmployeeResponse?> {
        // TODO: parse from request body
        val createdEmployee: EmployeeResponse? = employeeService.updateEmployee(id, request)
        return createdEmployee?.let {
            ApiResponse<EmployeeResponse?>(
                status = ApiResponseStatus.SUCCESS,
                data = createdEmployee,
                message = null
            )
        } ?: ApiResponse<EmployeeResponse?>(
            status = ApiResponseStatus.FAILURE,
            data = null,
            message = null
        )
    }

    @DeleteMapping("/employees/{id}")
    fun deleteEmployee(@PathVariable id: Long): ApiResponse<Nothing?> {
        employeeService.deleteEmployee(id)
        return ApiResponse(
            status = ApiResponseStatus.SUCCESS,
            data = null,
            message = null
        )
    }

}
