package ru.gadjetzona.features.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRecieveRemote(
    val email: String,
    val password: String
)


@Serializable
data class LoginResponseRemote(
    val token: String
)