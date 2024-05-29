package ru.gadjetzona.features.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRecieveRemote(
    val email: String,
    val password: String,
    val username: String?,
    val phonenumber: String?
)

@Serializable
data class RegisterResponseRemote(
    val token: String
)
