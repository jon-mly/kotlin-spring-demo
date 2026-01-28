package com.example.employee_api.service

import com.example.employee_api.model.dto.CreateEmployeeRequest
import com.example.employee_api.model.dto.EmployeeResponse
import com.example.employee_api.model.dto.UpdateEmployeeRequest
import com.example.employee_api.model.entities.EmployeeEntity
import com.example.employee_api.repository.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeServiceImpl(private val employeeRepository: EmployeeRepository) : EmployeeService {
    override fun getAllEmployees(): List<EmployeeResponse> {
        return employeeRepository.findAll().map { EmployeeResponse.fromEntity(it) }.toList()
    }

    override fun getEmployeeById(id: Long): EmployeeResponse? {
        return employeeRepository.findById(id).orElse(null)?.let { EmployeeResponse.fromEntity(it) }
    }

    override fun createEmployee(createEmployeeRequest: CreateEmployeeRequest): EmployeeResponse {
        val employee: EmployeeEntity = EmployeeEntity(
            employeeName = createEmployeeRequest.name,
            employeeAge = createEmployeeRequest.age,
            employeeSalary = createEmployeeRequest.salary,
            profileImage = createEmployeeRequest.profileImage,
        )
        return employeeRepository.save(employee).let { EmployeeResponse.fromEntity(it) }
    }

    override fun updateEmployee(
        id: Long,
        updateEmployeeRequest: UpdateEmployeeRequest
    ): EmployeeResponse? {
        val existing: EmployeeEntity = employeeRepository.findById(id).orElse(null) ?: return null

        existing.apply {
            updateEmployeeRequest.name?.let { employeeName = it }
            updateEmployeeRequest.age?.let { employeeAge = it }
            updateEmployeeRequest.salary?.let { employeeSalary = it }
            updateEmployeeRequest.profileImage?.let { profileImage = it }
        }

        return employeeRepository.save(existing).let { EmployeeResponse.fromEntity(it) }
    }

    override fun deleteEmployee(id: Long) {
        employeeRepository.deleteById(id)
    }
}