package com.example.demo.dto

import com.example.demo.domain.User

data class SignupRequest(
    val email: String,
    val password: String,
    val nickname: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class UserResponse(
    val id: Long,
    val email: String,
    val nickname: String
) {
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                id = user.id,
                email = user.email,
                nickname = user.nickname
            )
        }
    }
}
