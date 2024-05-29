package ru.gadjetzona.database.users

data class UserDTO (
    val id: Int? = null,
    val email: String,
    val password: String,
    val username: String?,
    val phonenumber: String?
)