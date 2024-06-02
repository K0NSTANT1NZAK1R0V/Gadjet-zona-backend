package ru.gadjetzona.features.catalog

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureCatalogRouting() {
    routing {
        route("/catalog") {
            get {
                val controller = CatalogController(call)
                controller.getCatalogItems()
            }
        }
    }
}