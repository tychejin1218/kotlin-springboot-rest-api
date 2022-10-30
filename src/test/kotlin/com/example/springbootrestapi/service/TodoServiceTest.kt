package com.example.springbootrestapi.service

import com.example.springbootrestapi.domain.TodoRequest
import com.example.springbootrestapi.mapper.TodoMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
class TodoServiceTest {

  @Autowired
  lateinit var todoService: TodoService

  @Autowired
  lateinit var todoMapper: TodoMapper

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

    val todoRequest = TodoRequest().apply {
      this.title = "Title Junit Test Insert"
      this.description = "Description Junit Test Insert"
      this.completed = true
    }

    // When
    val todoResponses = todoService.getTodos(todoRequest)

    // Then
    assertTrue(todoResponses.isNotEmpty())
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

    // When
    val todoResponse = todoService.getTodoById(insertId)

    // Then
    assertEquals(title, todoResponse.title)
    assertEquals(description, todoResponse.description)
    assertEquals(completed, todoResponse.completed)
  }

  @Transactional
  @DisplayName("insertTodo_To-Do 저장")
  @Test
  fun testInsertTodo() {

    // Given
    val todoRequest = TodoRequest().apply {
      this.title = "Title Junit Test Insert"
      this.description = "Description Junit Test Insert"
      this.completed = true
    }

    // When
    val todoResponse = todoService.insertTodo(todoRequest)

    // Then
    assertEquals(todoRequest.title, todoResponse.title)
    assertEquals(todoRequest.description, todoResponse.description)
    assertEquals(todoRequest.completed, todoResponse.completed)
  }

  @Transactional
  @DisplayName("updateTodo_To-Do 수정")
  @Test
  fun testUpdateTodo() {

    // Given
    val insertId = insertTodo("Title Junit Test Insert", "Description Junit Test Insert", false)

    val todoRequest = TodoRequest().apply {
      this.id = insertId
      this.title = "Title Junit Test Update"
      this.description = "Description Junit Test Upate"
      this.completed = true
    }

    // When
    val todoResponse = todoService.updateTodo(todoRequest)

    // Then
    assertEquals(todoRequest.title, todoResponse.title)
    assertEquals(todoRequest.description, todoResponse.description)
    assertEquals(todoRequest.completed, todoResponse.completed)
  }

  @Transactional
  @DisplayName("deleteTodoById_To-Do 삭제")
  @Test
  fun testDeleteTodoById() {

    // Given
    val insertId = insertTodo("Title Junit Test Insert", "Description Junit Test Insert", false)

    // When
    val todoResponse = todoService.deleteTodoById(insertId)

    // Then
    assertNull(todoResponse)
  }

  fun insertTodo(
    title: String,
    description: String,
    completed: Boolean
  ): Long {

    val todoRequest = TodoRequest().apply {
      this.title = title
      this.description = description
      this.completed = completed
    }

    todoMapper.insertTodo(todoRequest)

    return todoRequest.id ?: 0
  }
}