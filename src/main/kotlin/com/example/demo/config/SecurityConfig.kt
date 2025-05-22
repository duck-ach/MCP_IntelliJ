package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/login", "/signup", "/h2-console/**", "/", "/static/**").permitAll()
                    .anyRequest().permitAll() // 임시로 모든 요청 허용 (세션 기반으로 직접 관리)
            }
            .formLogin { form ->
                form.disable()
            }
            .httpBasic { basic ->
                basic.disable()
            }
            .csrf { csrf ->
                csrf.disable()
            }
            .headers { headers ->
                headers.frameOptions().disable()
            }

        return http.build()
    }
}
