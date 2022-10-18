package com.example.springbootrestapi.domain

import org.apache.ibatis.type.Alias

@Alias("TodoRequest")
data class TodoRequest(

  var id: Long? = null,
  var title: String = "",
  var description: String = "",
  var completed: Boolean = false
)

@Alias("TodoResponse")
data class TodoResponse(

  var id: Long? = null,
  var title: String = "",
  var description: String = "",
  var completed: Boolean = false
)