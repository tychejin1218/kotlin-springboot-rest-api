package com.example.springbootrestapi.service

import com.example.springbootrestapi.domain.TodoRequest
import com.example.springbootrestapi.domain.TodoResponse
import com.example.springbootrestapi.mapper.TodoMapper
import org.springframework.stereotype.Service

@Service
class TodoService(
  val todoMapper: TodoMapper
) {

  /** To-Do 조회  */
  fun getTodos(todoRequest: TodoRequest): MutableList<TodoResponse> {
    return todoMapper.getTodos(todoRequest)
  }
}