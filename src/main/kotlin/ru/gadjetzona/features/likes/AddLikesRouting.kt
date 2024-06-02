package ru.gadjetzona.features.likes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
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
            get("/{userId}") {
                val userId = call.parameters["userId"]?.toIntOrNull()
                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or missing userId")
                    return@get
                }
                val controller = LikesController(call)
                controller.getUserLikes(userId)
            }
        }
    }
}