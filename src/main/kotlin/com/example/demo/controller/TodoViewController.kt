package com.example.demo.controller

import com.example.demo.dto.TodoCreateRequest
import com.example.demo.dto.TodoUpdateRequest
import com.example.demo.service.TodoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/todos")
class TodoViewController(private val todoService: TodoService) {

    @GetMapping
    fun getTodoList(model: Model): String {
        val todos = todoService.getAllTodos()
        model.addAttribute("todos", todos)
        return "list"
    }

    @GetMapping("/create")
    fun createTodoForm(): String {
        return "create"
    }

    @PostMapping
    fun createTodo(@ModelAttribute request: TodoCreateRequest): String {
        todoService.createTodo(request)
        return "redirect:/todos"
    }

    @PostMapping("/{id}/toggle")
    fun toggleTodo(@PathVariable id: Long): String {
        val todo = todoService.getTodoById(id)
        val updateRequest = TodoUpdateRequest(
            isDone = !todo.isDone
        )
        todoService.updateTodo(id, updateRequest)
        return "redirect:/todos"
    }
}
