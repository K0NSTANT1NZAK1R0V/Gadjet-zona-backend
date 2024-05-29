package ru.gadjetzona.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.gadjetzona.cache.InMemoryCache
import ru.gadjetzona.cache.TokenCache
import ru.gadjetzona.utils.isValidEmail
import java.util.*

fun Application.configureRegisterRouting() {

    routing {
        post("/register") {
            val registerController = RegisterController(call)
            registerController.registerNewUser()
        }
    }
}