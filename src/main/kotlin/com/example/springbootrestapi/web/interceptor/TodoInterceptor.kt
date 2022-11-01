package com.example.springbootrestapi.web.interceptor

import com.example.springbootrestapi.web.controller.TodoController
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TodoInterceptor : HandlerInterceptor {

  private val log = LoggerFactory.getLogger(TodoController::class.java)

  override fun preHandle(
    request: HttpServletRequest,
    response: HttpServletResponse,
    handler: Any
  ): Boolean {
    val startTime = System.currentTimeMillis()
    log.debug(
      "Request URL::" + request.requestURL.toString() + ":: "
          + "Start Time=" + System.currentTimeMillis()
    )
    request.setAttribute("startTime", startTime)
    return true
  }

  override fun postHandle(
    request: HttpServletRequest,
    response: HttpServletResponse,
    handler: Any,
    modelAndView: ModelAndView?
  ) {
    log.debug(
      "Request URL::" + request.requestURL.toString() + " Sent to Handler :: "
          + "Current Time=" + System.currentTimeMillis()
    )
  }

  override fun afterCompletion(
    request: HttpServletRequest,
    response: HttpServletResponse,
    handler: Any,
    ex: Exception?
  ) {
    val startTime = request.getAttribute("startTime") as Long
    log.debug(
      "Request URL::" + request.requestURL.toString() + ":: "
          + "End Time=" + System.currentTimeMillis()
    )
    log.debug(
      "Request URL::" + request.requestURL.toString() + ":: "
          + "Time Taken=" + (System.currentTimeMillis() - startTime)
    )
  }
}