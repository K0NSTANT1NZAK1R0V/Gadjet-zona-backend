package ru.gadjetzona.features.itemcard

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureItemCardRouting() {
    routing {
        route("/item"){
            get("/{itemId}") {
                val itemId = call.parameters["itemId"]?.toIntOrNull()
                if (itemId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid item ID")
                    return@get
                }
                val controller = ItemCardController(call)
                controller.getItemById(itemId)
            }
        }
    }
}