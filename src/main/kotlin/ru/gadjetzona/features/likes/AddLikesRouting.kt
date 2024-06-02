package ru.gadjetzona.features.likes

import io.ktor.server.application.*
import io.ktor.server.routing.*

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