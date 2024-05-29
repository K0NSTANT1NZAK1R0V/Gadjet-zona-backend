package ru.gadjetzona.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.gadjetzona.cache.InMemoryCache
import ru.gadjetzona.cache.TokenCache
import ru.gadjetzona.features.register.RegisterRecieveRemote
import java.util.*


fun Application.configureLoginRouting() {

    routing {
        post("/login") {
            val loginController = LoginController(call)
            loginController.performLogin()
        }
    }
}