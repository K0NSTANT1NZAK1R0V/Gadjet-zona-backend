package ru.gadjetzona.features.orders

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.gadjetzona.features.order.OrderController


fun Application.configureOrderRouting() {
    routing {
        route("/order") {
            post("/add") {
                val controller = OrderController(call)
                controller.addOrder()
            }
            post("/remove") {
                val controller = OrderController(call)
                controller.removeOrder()
            }
            get("/{userId}") {
                val userId = call.parameters["userId"]?.toIntOrNull()
                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or missing userId")
                    return@get
                }
                val controller = OrderController(call)
                controller.getUserOrders(userId)
            }
        }
    }
}