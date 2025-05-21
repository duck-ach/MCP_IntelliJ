package com.example.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    fun home(): String {
        return "index"
    }

    @GetMapping("/**")
    fun fallback(): String {
        // 없는 경로 접근 시 index.html 보여주기 (SPA 스타일 or fallback)
        return "index"
    }

    @GetMapping("/view")
    fun redirectToTodoView(): String {
        return "redirect:/todos"
    }
}
