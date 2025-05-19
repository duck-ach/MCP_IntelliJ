package com.example.demo.controller

import com.example.demo.dto.TodoCreateRequest
import com.example.demo.dto.TodoResponse
import com.example.demo.dto.TodoUpdateRequest
import com.example.demo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(private val todoService: TodoService) {

    @PostMapping
    fun createTodo(@RequestBody request: TodoCreateRequest): ResponseEntity<TodoResponse> {
        val createdTodo = todoService.createTodo(request)
        return ResponseEntity(createdTodo, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllTodos(): ResponseEntity<List<TodoResponse>> {
        val todos = todoService.getAllTodos()
        return ResponseEntity(todos, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: Long): ResponseEntity<TodoResponse> {
        val todo = todoService.getTodoById(id)
        return ResponseEntity(todo, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateTodo(
        @PathVariable id: Long,
        @RequestBody request: TodoUpdateRequest
    ): ResponseEntity<TodoResponse> {
        val updatedTodo = todoService.updateTodo(id, request)
        return ResponseEntity(updatedTodo, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: Long): ResponseEntity<Unit> {
        todoService.deleteTodo(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
