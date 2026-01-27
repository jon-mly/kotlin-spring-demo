package com.example.employee_api.repository

import com.example.employee_api.model.entities.EmployeeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository // Not required, kept for explicitness
interface EmployeeRepository : JpaRepository<EmployeeEntity, Long>