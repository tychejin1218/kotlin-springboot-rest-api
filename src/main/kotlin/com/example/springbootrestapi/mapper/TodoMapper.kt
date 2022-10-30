package com.example.springbootrestapi.mapper

import com.example.springbootrestapi.domain.TodoRequest
import com.example.springbootrestapi.domain.TodoResponse
import org.springframework.stereotype.Repository

@Repository
interface TodoMapper {

  /** To-Do 목록 조회 */
  fun getTodos(todoRequest: TodoRequest): MutableList<TodoResponse>

  /** To-Do 상세 조회 */
  fun getTodoById(id: Long): TodoResponse

  /** To-Do 저장 */
  fun insertTodo(todoRequest: TodoRequest): Int

  /** To-Do 수정 */
  fun updateTodo(todoRequest: TodoRequest): Int

  /** To-Do 삭제 */
  fun deleteTodoById(id: Long): Int
}