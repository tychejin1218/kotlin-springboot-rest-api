package com.example.springbootrestapi.web.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController {

  @GetMapping("/api/todo")
  fun getTodo(): String {
    return "todo."
  }
}