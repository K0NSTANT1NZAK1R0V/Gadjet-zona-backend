package ru.gadjetzona.features.catalog

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureCatalogRouting() {
    routing {
        route("/catalog") {
            get {
                val controller = CatalogController(call)
                controller.getCatalogItems()
            }

            get("/category/{typeId}") {
                val typeId = call.parameters["typeId"]?.toIntOrNull()
                if (typeId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or missing typeId parameter")
                    return@get
                }
                val controller = CatalogController(call)
                controller.getCatalogItemsByCategory(typeId)
            }
        }
    }
}