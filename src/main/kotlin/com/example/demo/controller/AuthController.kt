package com.example.demo.controller

import com.example.demo.dto.LoginRequest
import com.example.demo.dto.SignupRequest
import com.example.demo.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class AuthController(private val userService: UserService) {

    @GetMapping("/signup")
    fun signupForm(): String {
        return "signup"
    }

    @PostMapping("/signup")
    fun signup(@ModelAttribute request: SignupRequest, model: Model): String {
        return try {
            userService.signup(request)
            "redirect:/login"
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
            "signup"
        }
    }

    @GetMapping("/login")
    fun loginForm(): String {
        return "login"
    }

    @PostMapping("/login")
    fun login(@ModelAttribute request: LoginRequest, session: HttpSession, model: Model): String {
        return try {
            val user = userService.login(request)
            session.setAttribute("user", user)
            "redirect:/todos"
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
            "login"
        }
    }

    @PostMapping("/logout")
    fun logout(session: HttpSession): String {
        session.invalidate()
        return "redirect:/login"
    }
}
