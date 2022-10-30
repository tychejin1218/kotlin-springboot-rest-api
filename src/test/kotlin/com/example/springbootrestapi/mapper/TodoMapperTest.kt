package com.example.springbootrestapi.mapper

import com.example.springbootrestapi.domain.TodoRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
class TodoMapperTest {

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

    var todoRequest = TodoRequest().apply {
      this.title = "Title Junit Test Insert"
      this.description = "Description Junit Test Insert"
      this.completed = true
    }

    // When
    var todos = todoMapper.getTodos(todoRequest)

    // Then
    assertTrue(!todos.isEmpty())
  }

  @Transactional
  @DisplayName("getTodoById_To-Do 상세 조회")
  @Test
  fun testGetTodoById() {

    // Given
    var insertId = insertTodo("Title Junit Test Insert 01", "Description Junit Test Insert 01", false)

    // When
    var todoResponse = todoMapper.getTodoById(insertId)

    // Then
    assertEquals(insertId, todoResponse.id)
  }

  @Transactional
  @DisplayName("insertTodo_To-Do 저장")
  @Test
  fun testInsertTodo() {

    // Given
    var todoRequest = TodoRequest().apply {
      this.title = "Title Junit Test Insert"
      this.description = "Description Junit Test Insert"
      this.completed = false
    }

    // When
    todoMapper.insertTodo(todoRequest)

    // Then
    todoRequest.id?.let {
      var todoResponse = todoMapper.getTodoById(it)
      Assertions.assertEquals(todoRequest.title, todoResponse.title)
      Assertions.assertEquals(todoRequest.description, todoResponse.description)
      Assertions.assertEquals(todoRequest.completed, todoResponse.completed)
    } ?: throw Exception()
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
      this.description = "Description Junit Test Update"
      this.completed = true
    }

    // When
    todoMapper.updateTodo(todoRequest)

    // Then
    todoRequest.id?.let {
      var todoResponse = todoMapper.getTodoById(it)
      Assertions.assertEquals(todoRequest.title, todoResponse.title)
      Assertions.assertEquals(todoRequest.description, todoResponse.description)
      Assertions.assertEquals(todoRequest.completed, todoResponse.completed)
    } ?: throw Exception()
  }

  @Transactional
  @DisplayName("deleteTodoById_To-Do 삭제")
  @Test
  fun testDeleteTodoById() {

    // Given
    val insertId = insertTodo("Title Junit Test Insert", "Description Junit Test Insert", false)

    // When
    todoMapper.deleteTodoById(insertId)

    // Then
    val todoResponse = todoMapper.getTodoById(insertId)
    Assertions.assertNull(todoResponse)
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