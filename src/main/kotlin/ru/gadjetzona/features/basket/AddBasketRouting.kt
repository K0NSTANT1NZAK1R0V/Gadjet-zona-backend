package ru.gadjetzona.features.basket

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureBasketRouting() {
    routing {
        route("/basket") {
            post("/add") {
                val controller = BasketController(call)
                controller.addItemToBasket()
            }
            post("/remove") {
                val controller = BasketController(call)
                controller.removeItemFromBasket()
            }
            get("/{userId}") {
                val userId = call.parameters["userId"]?.toIntOrNull()
                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or missing userId")
                    return@get
                }
                val controller = BasketController(call)
                controller.getUserBasketItems(userId)
            }
        }
    }
}