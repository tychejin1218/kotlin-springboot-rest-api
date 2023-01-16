package com.example.springbootrestapi.web.controller

import com.example.springbootrestapi.domain.TodoRequest
import com.example.springbootrestapi.domain.TodoResponse
import com.example.springbootrestapi.service.TodoService
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class TodoController(
  val todoService: TodoService
) {

  private val log = LoggerFactory.getLogger(TodoController::class.java)

  /**
   * To-Do 목록 조회
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
    log.info("todoRequest:[{}]", todoRequest.toString())
    return todoService.getTodos(todoRequest)
  }

  /**
   * To-Do 상세 조회
   */
  @GetMapping(
    value = ["/api/todo/{id}"],
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE]
  )
  fun getTodoById(
    request: HttpServletRequest,
    response: HttpServletResponse,
    @PathVariable id: Long
  ): TodoResponse {
    log.info("id:[{}]", id)
    return todoService.getTodoById(id)
  }

  /**
   * To-Do 저장
   */
  @PostMapping(
    value = ["/api/todo"],
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE]
  )
  fun insertTodo(
    request: HttpServletRequest,
    response: HttpServletResponse,
    @RequestBody todoRequest: TodoRequest
  ): TodoResponse {
    log.info("todoRequest:[{}]", todoRequest.toString())
    return todoService.insertTodo(todoRequest)
  }

  /**
   * To-Do 수정
   */
  @PutMapping(
    value = ["/api/todo"],
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE]
  )
  fun updateTodo(
    request: HttpServletRequest,
    response: HttpServletResponse,
    @RequestBody todoRequest: TodoRequest
  ): TodoResponse {
    log.info("todoRequest:[{}]", todoRequest.toString())
    return todoService.updateTodo(todoRequest)
  }

  /**
   * To-Do 삭제
   */
  @DeleteMapping(
    value = ["/api/todo/{id}"],
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE]
  )
  fun deleteTodo(
    request: HttpServletRequest,
    response: HttpServletResponse,
    @PathVariable id: Long
  ): TodoResponse {
    log.info("id:[{}]", id)
    return todoService.deleteTodoById(id)
  }
}