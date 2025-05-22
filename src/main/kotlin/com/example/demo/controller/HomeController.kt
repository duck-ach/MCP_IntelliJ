package com.example.demo.controller

import com.example.demo.dto.UserResponse
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    fun home(session: HttpSession): String {
        val sessionUser = session.getAttribute("user") as? UserResponse
        return if (sessionUser != null) {
            "redirect:/todos"
        } else {
            "index"
        }
    }

    @GetMapping("/view")
    fun redirectToTodoView(): String {
        return "redirect:/todos"
    }
}
