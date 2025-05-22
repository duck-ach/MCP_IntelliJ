package com.example.demo.controller

import com.example.demo.dto.TodoCreateRequest
import com.example.demo.dto.TodoResponse
import com.example.demo.dto.TodoUpdateRequest
import com.example.demo.dto.UserResponse
import com.example.demo.service.TodoService
import com.example.demo.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// API 컨트롤러는 현재 비활성화 (세션 기반 View 컨트롤러만 사용)
// @RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService,
    private val userService: UserService
) {

    @PostMapping
    fun createTodo(@RequestBody request: TodoCreateRequest, session: HttpSession): ResponseEntity<TodoResponse> {
        val sessionUser = session.getAttribute("user") as? UserResponse
            ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        
        val user = userService.findByEmail(sessionUser.email)
            ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        
        val createdTodo = todoService.createTodo(request, user)
        return ResponseEntity(createdTodo, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllTodos(session: HttpSession): ResponseEntity<List<TodoResponse>> {
        val sessionUser = session.getAttribute("user") as? UserResponse
            ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        
        val user = userService.findByEmail(sessionUser.email)
            ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        
        val todos = todoService.getAllTodosByUser(user)
        return ResponseEntity(todos, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: Long, session: HttpSession): ResponseEntity<TodoResponse> {
        val sessionUser = session.getAttribute("user") as? UserResponse
            ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        
        val user = userService.findByEmail(sessionUser.email)
            ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        
        val todo = todoService.getTodoByIdAndUser(id, user)
        return ResponseEntity(todo, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateTodo(
        @PathVariable id: Long,
        @RequestBody request: TodoUpdateRequest,
        session: HttpSession
    ): ResponseEntity<TodoResponse> {
        val sessionUser = session.getAttribute("user") as? UserResponse
            ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        
        val user = userService.findByEmail(sessionUser.email)
            ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        
        val updatedTodo = todoService.updateTodo(id, request, user)
        return ResponseEntity(updatedTodo, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: Long, session: HttpSession): ResponseEntity<Unit> {
        val sessionUser = session.getAttribute("user") as? UserResponse
            ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        
        val user = userService.findByEmail(sessionUser.email)
            ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        
        todoService.deleteTodo(id, user)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
