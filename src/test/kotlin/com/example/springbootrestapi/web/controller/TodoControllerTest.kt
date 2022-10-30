package com.example.springbootrestapi.web.controller

import com.example.springbootrestapi.domain.TodoRequest
import com.example.springbootrestapi.mapper.TodoMapper
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("local")
class TodoControllerTest {

  @Autowired
  lateinit var todoMapper: TodoMapper

  @Autowired
  lateinit var mockMvc: MockMvc

  @Autowired
  lateinit var objectMapper: ObjectMapper

  @Transactional
  @DisplayName("getTodos_To-Do 목록 조회")
  @Test
  fun testGetTodos() {

    // Given
    insertTodo("Title Junit Test Insert 01", "Description Junit Test Insert 01", false)
    insertTodo("Title Junit Test Insert 02", "Description Junit Test Insert 02", true)
    insertTodo("Title Junit Test Insert 03", "Description Junit Test Insert 03", false)
    insertTodo("Title Junit Test Insert 04", "Description Junit Test Insert 04", true)
    insertTodo("Title Junit Test Insert 05", "Description Junit Test Insert 05", false)

    val url = "/api/todos"
    val todoRequest = TodoRequest().apply {
      this.title = "Title Junit Test Insert"
      this.description = "Description Junit Test Insert"
      this.completed = true
    }

    // When
    val resultActions = mockMvc.perform(
      MockMvcRequestBuilders.post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(todoRequest))
    )

    // Then
    resultActions
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.length()", `is`(2)))
      .andDo(print())
  }

  @Transactional
  @DisplayName("getTodoById_To-Do 상세 조회")
  @Test
  fun testGetTodoById() {

    // Given
    val title = "Title Junit Test Insert"
    val description = "Description Junit Test Insert"
    val completed = false
    val insertId = insertTodo(title, description, completed)
    val url = "/api/todo/$insertId"

    // When
    val resultActions = mockMvc.perform(
      get(url)
        .contentType(MediaType.APPLICATION_JSON)
    )

    // Then
    resultActions
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.title", equalTo(title)))
      .andExpect(jsonPath("$.description", equalTo(description)))
      .andExpect(jsonPath("$.completed", equalTo(completed)))
      .andDo(print())
  }

  @Transactional
  @DisplayName("insertTodo_To-Do 저장")
  @Test
  fun testInsertTodo() {

    // Given
    val url = "/api/todo"
    val todoRequest = TodoRequest().apply {
      this.title = "Title Junit Test Insert"
      this.description = "Description Junit Test Insert"
      this.completed = true
    }

    // When
    val resultActions = mockMvc.perform(
      post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(todoRequest))
    )

    // Then
    resultActions
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.title", equalTo(todoRequest.title)))
      .andExpect(jsonPath("$.description", equalTo(todoRequest.description)))
      .andExpect(jsonPath("$.completed", equalTo(todoRequest.completed)))
      .andDo(print())
  }

  @Transactional
  @DisplayName("updateTodo_To-Do 수정")
  @Test
  fun testUpdateTodo() {

    // Given
    val title = "Title Junit Test Insert"
    val description = "Description Junit Test Insert"
    val completed = false
    val insertId = insertTodo(title, description, completed)
    val url = "/api/todo"
    val todoRequest = TodoRequest().apply {
      this.id = insertId
      this.title = "Title Junit Test Insert"
      this.description = "Description Junit Test Insert"
      this.completed = true
    }

    // When
    val resultActions = mockMvc.perform(
      put(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(todoRequest))
    )

    // Then
    resultActions
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.title", equalTo(todoRequest.title)))
      .andExpect(jsonPath("$.description", equalTo(todoRequest.description)))
      .andExpect(jsonPath("$.completed", equalTo(todoRequest.completed)))
      .andDo(print())
  }

  @Transactional
  @DisplayName("deleteTodo_To-Do 삭제")
  @Test
  fun testDeleteTodo() {

    // Given
    val title = "Title Junit Test Insert"
    val description = "Description Junit Test Insert"
    val completed = false
    val insertId = insertTodo(title, description, completed)
    val url = "/api/todo/$insertId"

    // When
    val resultActions = mockMvc.perform(
      delete(url)
        .contentType(MediaType.APPLICATION_JSON)
    )

    // Then
    resultActions
      .andExpect(status().isOk)
      .andDo(print())
  }

  fun insertTodo(
    title: String,
    description: String,
    completed: Boolean
  ): Long {

    var todoRequest = TodoRequest().apply {
      this.title = title
      this.description = description
      this.completed = completed
    }

    todoMapper.insertTodo(todoRequest)

    return todoRequest.id ?: 0
  }
}