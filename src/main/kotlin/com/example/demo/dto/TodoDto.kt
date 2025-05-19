package com.example.demo.dto

import com.example.demo.domain.Todo

data class TodoCreateRequest(
    val title: String,
    val description: String? = null
)

data class TodoUpdateRequest(
    val title: String? = null,
    val description: String? = null,
    val isDone: Boolean? = null
)

data class TodoResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val isDone: Boolean
) {
    companion object {
        fun from(todo: Todo): TodoResponse {
            return TodoResponse(
                id = todo.id,
                title = todo.title,
                description = todo.description,
                isDone = todo.isDone
            )
        }
    }
}
