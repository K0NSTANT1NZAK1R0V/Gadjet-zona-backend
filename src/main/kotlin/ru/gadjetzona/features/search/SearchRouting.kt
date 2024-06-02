package ru.gadjetzona.features.search

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Application.configureItemRouting() {
    routing {
        route("/items") {
            get("/search") {
                val query = call.parameters["query"] ?: ""
                if (query.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "Query parameter is missing or empty")
                    return@get
                }
                val controller = ItemController(call)
                controller.searchItems(query)
            }
        }
    }
}