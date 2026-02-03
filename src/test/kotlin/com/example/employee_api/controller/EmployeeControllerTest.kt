package com.example.employee_api.controller

import com.example.employee_api.model.dto.ApiResponseStatus
import com.example.employee_api.model.dto.EmployeeResponse
import com.example.employee_api.service.EmployeeService
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.Test

@WebMvcTest(EmployeeController::class)
class EmployeeControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var employeeService: EmployeeService

    @Test
    fun `Get employees returns 200 with list`() {
        val employees: List<EmployeeResponse> = listOf(
            EmployeeResponse(1, "John", 30, 50000, null)
        )

        whenever(employeeService.getAllEmployees()).thenReturn(employees)

        mockMvc.perform(get("/api/v1/employees"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(ApiResponseStatus.SUCCESS.value))
            .andExpect(jsonPath("$.data[0].name").value("John"))
    }

    @Test
    fun `Get employee by ID returns 404 when not found`() {
        whenever(employeeService.getEmployeeById(999)).thenReturn(null)

        mockMvc.perform(get("/api/v1/employees/999"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(ApiResponseStatus.FAILURE.value))
    }

    @Test
    fun `POST employee returns 400 when age below minimum`() {
        mockMvc.perform(
            post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"name": "Young", "age": 10, "salary": 50000}""")
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(ApiResponseStatus.FAILURE.value))
    }

    @Test
    fun `POST employee returns 400 when name is blank`() {
        mockMvc.perform(
            post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"name": "", "age": 30, "salary": 50000}""")
        )
            .andExpect(status().isBadRequest)
    }


}