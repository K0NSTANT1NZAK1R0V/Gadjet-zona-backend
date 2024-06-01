package ru.gadjetzona.features.addtolikes

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.gadjetzona.features.addtobasket.BasketController

fun Application.configureLikesRouting() {
    routing {
        route("/likes") {
            post("/add") {
                val controller = LikesController(call)
                controller.addItemToLikes()
            }
            post("/remove") {
                val controller = LikesController(call)
                controller.removeItemFromLikes()
            }
        }
    }
}