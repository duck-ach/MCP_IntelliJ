package com.example.demo.config

import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.DispatcherServlet

@Configuration
class WebConfig {

    @Bean
    fun dispatcherServlet(): DispatcherServlet {
        val servlet = DispatcherServlet()
        return servlet;
    }

    @Bean
    fun dispatcherServletRegistration(): ServletRegistrationBean<DispatcherServlet> {
        val registration = ServletRegistrationBean(dispatcherServlet(), "/")
        return registration
    }

}