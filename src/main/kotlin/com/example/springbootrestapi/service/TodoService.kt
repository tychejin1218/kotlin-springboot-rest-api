package com.example.springbootrestapi.service

import com.example.springbootrestapi.domain.TodoRequest
import com.example.springbootrestapi.domain.TodoResponse
import com.example.springbootrestapi.mapper.TodoMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class TodoService(
  val todoMapper: TodoMapper
) {

  /** To-Do 조회  */
  fun getTodos(todoRequest: TodoRequest): MutableList<TodoResponse> {
    return todoMapper.getTodos(todoRequest)
  }

  /**
   * To-Do 상세 조회
   */
  fun getTodoById(id: Long): TodoResponse {
    return todoMapper.getTodoById(id)
  }

  /**
   * To-Do 저장
   */
  fun insertTodo(todoRequest: TodoRequest): TodoResponse {
    var todoResponse = TodoResponse()
    var result = todoMapper.insertTodo(todoRequest)
    if(result > 0){
      todoRequest.id?.let {
        todoResponse = todoMapper.getTodoById(it)
      }
    }
    return todoResponse
  }

  /**
   * To-Do 수정
   */
  fun updateTodo(todoRequest: TodoRequest): TodoResponse {
    var todoResponse = TodoResponse()
    var result = todoMapper.updateTodo(todoRequest)
    if (result > 0) {
      todoRequest.id?.let {
        todoResponse = todoMapper.getTodoById(it)
      }
    }
    return todoResponse
  }

  /**
   * To-Do 삭제
   */
  fun deleteTodoById(id: Long): TodoResponse {
    var todoResponse = TodoResponse()
    var result = todoMapper.deleteTodoById(id)
    if (result > 0) {
      todoResponse = todoMapper.getTodoById(id)
    }
    return todoResponse
  }

  /**
   * To-Do 저장 시 title이 #으로 시작하는 경우 RuntimeException 발생
   */
  @Transactional
  fun insertTodosFailed(todoRequests: List<TodoRequest>): Int {
    var result = 0
    for (todoRequest in todoRequests) {
      if (todoRequest.title.startsWith("#")) {
        throw RuntimeException("title이 #으로 시작")
      }
      result += todoMapper.insertTodo(todoRequest)
    }
    return result
  }
}