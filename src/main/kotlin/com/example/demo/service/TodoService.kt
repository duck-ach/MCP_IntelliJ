package com.example.demo.service

import com.example.demo.domain.Todo
import com.example.demo.domain.User
import com.example.demo.dto.TodoCreateRequest
import com.example.demo.dto.TodoResponse
import com.example.demo.dto.TodoUpdateRequest
import com.example.demo.repository.TodoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(private val todoRepository: TodoRepository) {

    // 사용자별 투두 생성
    @Transactional
    fun createTodo(request: TodoCreateRequest, user: User): TodoResponse {
        val todo = Todo(
            title = request.title,
            description = request.description,
            user = user
        )
        
        val savedTodo = todoRepository.save(todo)
        return TodoResponse.from(savedTodo)
    }

    // 기존 메서드 (사용자 정보 없이) - 임시로 유지
    @Transactional
    fun createTodo(request: TodoCreateRequest): TodoResponse {
        throw IllegalStateException("사용자 정보가 필요합니다.")
    }

    // 사용자별 투두 조회
    @Transactional(readOnly = true)
    fun getAllTodosByUser(user: User): List<TodoResponse> {
        return todoRepository.findByUser(user).map { TodoResponse.from(it) }
    }

    // 기존 메서드 (사용자 정보 없이) - 임시로 유지
    @Transactional(readOnly = true)
    fun getAllTodos(): List<TodoResponse> {
        throw IllegalStateException("사용자 정보가 필요합니다.")
    }

    @Transactional(readOnly = true)
    fun getTodoByIdAndUser(id: Long, user: User): TodoResponse {
        val todo = todoRepository.findByUserAndId(user, id)
            ?: throw NoSuchElementException("Todo not found with id: $id")
        
        return TodoResponse.from(todo)
    }

    // 기존 메서드 (사용자 정보 없이) - 임시로 유지
    @Transactional(readOnly = true)
    fun getTodoById(id: Long): TodoResponse {
        val todo = todoRepository.findById(id).orElseThrow {
            NoSuchElementException("Todo not found with id: $id")
        }
        return TodoResponse.from(todo)
    }

    @Transactional
    fun updateTodo(id: Long, request: TodoUpdateRequest, user: User): TodoResponse {
        val todo = todoRepository.findByUserAndId(user, id)
            ?: throw NoSuchElementException("Todo not found with id: $id")
        
        request.title?.let { todo.title = it }
        request.description?.let { todo.description = it }
        request.isDone?.let { todo.isDone = it }
        
        val updatedTodo = todoRepository.save(todo)
        return TodoResponse.from(updatedTodo)
    }

    // 기존 메서드 (사용자 정보 없이) - 임시로 유지
    @Transactional
    fun updateTodo(id: Long, request: TodoUpdateRequest): TodoResponse {
        throw IllegalStateException("사용자 정보가 필요합니다.")
    }

    @Transactional
    fun deleteTodo(id: Long, user: User) {
        val todo = todoRepository.findByUserAndId(user, id)
            ?: throw NoSuchElementException("Todo not found with id: $id")
        
        todoRepository.delete(todo)
    }
}
