package com.example.demo.domain

import jakarta.persistence.*

@Entity
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    var title: String,
    
    @Column
    var description: String? = null,
    
    @Column
    var isDone: Boolean = false
)
