package ru.gadjetzona.features.basket

import io.ktor.server.application.*
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
        }
    }
}