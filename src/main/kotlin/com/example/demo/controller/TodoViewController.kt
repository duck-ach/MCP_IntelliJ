package com.example.demo.controller

import com.example.demo.dto.TodoCreateRequest
import com.example.demo.dto.TodoUpdateRequest
import com.example.demo.dto.UserResponse
import com.example.demo.service.TodoService
import com.example.demo.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/todos")
class TodoViewController(
    private val todoService: TodoService,
    private val userService: UserService
) {

    @GetMapping
    fun getTodoList(model: Model, session: HttpSession): String {
        val sessionUser = session.getAttribute("user") as? UserResponse
            ?: return "redirect:/login"
        
        val user = userService.findByEmail(sessionUser.email)
            ?: return "redirect:/login"
        
        val todos = todoService.getAllTodosByUser(user)
        model.addAttribute("todos", todos)
        model.addAttribute("nickname", sessionUser.nickname)
        return "list"
    }

    @GetMapping("/create")
    fun createTodoForm(session: HttpSession): String {
        session.getAttribute("user") as? UserResponse
            ?: return "redirect:/login"
        
        return "create"
    }

    @PostMapping
    fun createTodo(@ModelAttribute request: TodoCreateRequest, session: HttpSession): String {
        val sessionUser = session.getAttribute("user") as? UserResponse
            ?: return "redirect:/login"
        
        val user = userService.findByEmail(sessionUser.email)
            ?: return "redirect:/login"
        
        todoService.createTodo(request, user)
        return "redirect:/todos"
    }

    @PostMapping("/{id}/toggle")
    fun toggleTodo(@PathVariable id: Long, session: HttpSession): String {
        val sessionUser = session.getAttribute("user") as? UserResponse
            ?: return "redirect:/login"
        
        val user = userService.findByEmail(sessionUser.email)
            ?: return "redirect:/login"
        
        val todo = todoService.getTodoByIdAndUser(id, user)
        val updateRequest = TodoUpdateRequest(
            isDone = !todo.isDone
        )
        todoService.updateTodo(id, updateRequest, user)
        return "redirect:/todos"
    }
}
