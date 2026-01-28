package com.example.employee_api.service

import com.example.employee_api.model.dto.CreateEmployeeRequest
import com.example.employee_api.model.dto.EmployeeResponse
import com.example.employee_api.model.dto.UpdateEmployeeRequest

interface EmployeeService {
    fun getAllEmployees(): List<EmployeeResponse>
    fun getEmployeeById(id: Long): EmployeeResponse?
    fun createEmployee(createEmployeeRequest: CreateEmployeeRequest): EmployeeResponse
    fun updateEmployee(id: Long, updateEmployeeRequest: UpdateEmployeeRequest): EmployeeResponse?
    fun deleteEmployee(id: Long)
}