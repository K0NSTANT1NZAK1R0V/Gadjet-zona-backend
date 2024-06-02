package ru.gadjetzona.features.order

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.gadjetzona.features.orders.OrderController

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
        }
    }
}