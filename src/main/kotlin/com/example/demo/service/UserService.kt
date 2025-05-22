package com.example.demo.service

import com.example.demo.domain.User
import com.example.demo.dto.LoginRequest
import com.example.demo.dto.UserResponse
import com.example.demo.dto.SignupRequest
import com.example.demo.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun signup(request: SignupRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }

        val user = User(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            nickname = request.nickname
        )

        val savedUser = userRepository.save(user)
        return UserResponse.from(savedUser)
    }

    @Transactional(readOnly = true)
    fun login(request: LoginRequest): UserResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("존재하지 않는 이메일입니다.")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }

        return UserResponse.from(user)
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): UserResponse {
        val user = userRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("존재하지 않는 사용자입니다.")
        }
        return UserResponse.from(user)
    }

    @Transactional(readOnly = true)
    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
}
