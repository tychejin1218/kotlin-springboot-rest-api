package com.example.springbootrestapi.mapper

import com.example.springbootrestapi.domain.TodoRequest
import com.example.springbootrestapi.domain.TodoResponse
import org.springframework.stereotype.Repository

@Repository
interface TodoMapper {

  /** To-Do 조회 */
  fun getTodos(todoRequest: TodoRequest): MutableList<TodoResponse>
}