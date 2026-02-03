package com.example.employee_api.integration

import com.example.employee_api.model.dto.ApiResponse
import com.example.employee_api.model.dto.EmployeeResponse
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity.post
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.readValue
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `full CRUD flow`() {

        // Create
        val createResult: MvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"name": "Young", "age": 30, "salary": 50000}""")
        ).andExpect(status().isCreated)
            .andExpect(jsonPath("$.data.name").value("Young"))
            .andReturn()

        val created: ApiResponse<EmployeeResponse> = objectMapper.readValue(createResult.response.contentAsString)
        val employeeId: Long? = created.data?.id

        assertThat(employeeId).isNotNull()
        assertThat(created.data?.name).isEqualTo("Young")
        assertThat(created.data?.age).isEqualTo(30)

        // Get
        val getResult: MvcResult = mockMvc
            .perform(get("/api/v1/employees/$employeeId")).andExpect(status().isOk).andReturn()

        val fetched: ApiResponse<EmployeeResponse> = objectMapper.readValue(getResult.response.contentAsString)
        assertThat(fetched.data?.name).isEqualTo("Young")

        // Update
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/employees/$employeeId")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"name": "John", "salary": 60000}""")
        ).andExpect(status().isOk).andExpect(jsonPath("$.data.name").value("John"))
            .andExpect(jsonPath("$.data.salary").value(60000))
            .andExpect(jsonPath("$.data.age").value(30))

        // List all
        mockMvc
            .perform(get("/api/v1/employees")).andExpect(status().isOk)
            .andExpect(jsonPath("$.data[?(@.id == $employeeId)].name").value("John"))

        // Delete
        mockMvc
            .perform(delete("/api/v1/employees/$employeeId")).andExpect(status().isNoContent)
        mockMvc
            .perform(get("/api/v1/employees/$employeeId")).andExpect(status().isNotFound)


    }

}