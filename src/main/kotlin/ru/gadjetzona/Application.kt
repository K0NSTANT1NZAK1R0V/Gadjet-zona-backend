package ru.gadjetzona

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import ru.gadjetzona.features.basket.configureBasketRouting
import ru.gadjetzona.features.catalog.configureCatalogRouting
import ru.gadjetzona.features.likes.configureLikesRouting
import ru.gadjetzona.features.login.configureLoginRouting
import ru.gadjetzona.features.orders.configureOrderRouting
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
    configureCatalogRouting()
    configureOrderRouting()
    configureRegisterRouting()
    configureSerialization()
}
