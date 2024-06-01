package ru.gadjetzona

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import ru.gadjetzona.features.addtobasket.configureBasketRouting
import ru.gadjetzona.features.addtolikes.configureLikesRouting
import ru.gadjetzona.features.login.configureLoginRouting
import ru.gadjetzona.plugins.*
import ru.gadjetzona.features.register.configureRegisterRouting

fun main() {
    Database.connect("jdbc:postgresql://localhost:5432/gadjetzone", driver = "org.postgresql.Driver", user = "postgres",  password = "1234567890")

    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureLoginRouting()
    configureLikesRouting()
    configureBasketRouting()
    configureRegisterRouting()
    configureSerialization()
}
