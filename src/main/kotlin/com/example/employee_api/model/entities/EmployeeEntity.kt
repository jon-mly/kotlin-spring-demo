package com.example.employee_api.model.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

// @Column(column = ...) not required since Spring Boot adds camel to snake case conversion strategy

@Entity(name = "employees")
class EmployeeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    var employeeName: String = "",

    var employeeSalary: Int = 0,

    var employeeAge: Int = 0,

    var profileImage: String? = null
)
