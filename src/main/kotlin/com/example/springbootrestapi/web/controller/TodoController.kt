package com.example.springbootrestapi.web.controller

import com.example.springbootrestapi.domain.TodoRequest
import com.example.springbootrestapi.domain.TodoResponse
import com.example.springbootrestapi.service.TodoService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class TodoController(
  val todoService: TodoService
) {

  /**
   * To-Do 조회
   */
  @PostMapping(
    value = ["/api/todos"],
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE]
  )
  fun getTodos(
    request: HttpServletRequest,
    response: HttpServletResponse,
    @RequestBody todoRequest: TodoRequest
  ): MutableList<TodoResponse> {
    return todoService.getTodos(todoRequest)
  }
}