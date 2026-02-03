package com.example.employee_api.repository

import com.example.employee_api.model.entities.EmployeeEntity
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import kotlin.test.Test

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    @Test
    fun `save and find bu ID works`() {

        val employee: EmployeeEntity = EmployeeEntity(
            employeeName = "Test",
            employeeAge = 30,
            employeeSalary = 50000,
            profileImage = null
        )

        val saved: EmployeeEntity = employeeRepository.save(employee)
        val found: EmployeeEntity? = employeeRepository.findById(saved.id).orElse(null)

        assertThat(found).isNotNull()
        assertThat(found?.employeeName).isEqualTo(employee.employeeName)
    }

    @Test
    fun `find by ID returns empty when not found`() {
        val found: EmployeeEntity? = employeeRepository.findById(999).orElse(null)

        assertThat(found).isNull()
    }

    @Test
    fun `delete removes entity`() {
        val employee: EmployeeEntity = employeeRepository.save(
            EmployeeEntity(
                employeeName = "Test",
                employeeAge = 30,
                employeeSalary = 50000,
                profileImage = null
            )
        )

        employeeRepository.deleteById(employee.id)

        assertThat(employeeRepository.findById(employee.id).orElse(null)).isNull()


    }

}