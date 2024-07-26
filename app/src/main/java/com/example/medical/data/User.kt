package com.example.medical.data

data class User(
    val username: String,
    val email: String,
    val numero: String, // Assuming 'numero' is a typo for phone number
    val password: String,
    val isNew: Boolean = false
)