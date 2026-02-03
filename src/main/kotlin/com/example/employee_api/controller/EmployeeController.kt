package com.example.employee_api.controller

import com.example.employee_api.exception.ResourceNotFoundException
import com.example.employee_api.model.dto.ApiResponse
import com.example.employee_api.model.dto.ApiResponseStatus
import com.example.employee_api.model.dto.CreateEmployeeRequest
import com.example.employee_api.model.dto.EmployeeResponse
import com.example.employee_api.model.dto.UpdateEmployeeRequest
import com.example.employee_api.service.EmployeeService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

//    companion object {
//        val mockEmployees: List<EmployeeResponse> = listOf(
//            EmployeeResponse(id = 1, name = "First employee", age = 30, salary = 15000, profileImg = null),
//            EmployeeResponse(id = 2, name = "Second employee", age = 35, salary = 56000, profileImg = null),
//            EmployeeResponse(id = 3, name = "Third employee", age = 62, salary = 999999, profileImg = null),
//        )
//    }

    @GetMapping("/employees")
    fun getAllEmployees(): ResponseEntity<ApiResponse<List<EmployeeResponse>>> {
        val employees: List<EmployeeResponse> = employeeService.getAllEmployees()
        return ResponseEntity<ApiResponse<List<EmployeeResponse>>>.status(HttpStatus.OK).body(
            ApiResponse<List<EmployeeResponse>>(
                status = ApiResponseStatus.SUCCESS,
                data = employees,
                message = null
            )
        )
    }

    @GetMapping("/employees/{id}")
    fun getEmployeeById(@PathVariable id: Long): ResponseEntity<ApiResponse<EmployeeResponse>> {
        val employee: EmployeeResponse? = employeeService.getEmployeeById(id)
        return employee?.let {
            ResponseEntity<ApiResponse<EmployeeResponse>>.status(HttpStatus.OK).body(
                ApiResponse<EmployeeResponse>(
                    status = ApiResponseStatus.SUCCESS,
                    data = it,
                    message = null
                )
            )
        } ?: throw ResourceNotFoundException("employee with id $id not found")
    }

    @PostMapping("/employees")
    fun createEmployee(@Valid @RequestBody request: CreateEmployeeRequest): ResponseEntity<ApiResponse<EmployeeResponse>> {
        val createEmployee: EmployeeResponse = employeeService.createEmployee(request)
        return ResponseEntity<ApiResponse<EmployeeResponse>>.status(HttpStatus.CREATED).body(
            ApiResponse<EmployeeResponse>(
                status = ApiResponseStatus.SUCCESS,
                data = createEmployee,
                message = null
            )
        )
    }

    @PutMapping("/employees/{id}")
    fun updateEmployee(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateEmployeeRequest
    ): ResponseEntity<ApiResponse<EmployeeResponse>> {
        // TODO: parse from request body
        val createdEmployee: EmployeeResponse? = employeeService.updateEmployee(id, request)
        return createdEmployee?.let {
            ResponseEntity<ApiResponse<EmployeeResponse>>.status(HttpStatus.OK).body(
                ApiResponse<EmployeeResponse>(
                    status = ApiResponseStatus.SUCCESS,
                    data = createdEmployee,
                    message = null
                )
            )
        } ?: throw ResourceNotFoundException("employee with id $id not found")
    }

    @DeleteMapping("/employees/{id}")
    fun deleteEmployee(@PathVariable id: Long): ResponseEntity<ApiResponse<Nothing>> {
        employeeService.deleteEmployee(id)
        return ResponseEntity<ApiResponse<Nothing>>.status(HttpStatus.NO_CONTENT).body(
            ApiResponse(
                status = ApiResponseStatus.SUCCESS,
                data = null,
                message = null
            )
        )
    }

}
