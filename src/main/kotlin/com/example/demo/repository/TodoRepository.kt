package com.example.demo.repository

import com.example.demo.domain.Todo
import com.example.demo.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long> {
    fun findByUser(user: User): List<Todo>
    fun findByUserAndId(user: User, id: Long): Todo?
}
