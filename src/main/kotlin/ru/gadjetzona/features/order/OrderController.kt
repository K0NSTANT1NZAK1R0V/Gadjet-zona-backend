package ru.gadjetzona.features.order

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ru.gadjetzona.database.orders.OrdersDTO
import ru.gadjetzona.database.orders.Orders
import org.slf4j.LoggerFactory

class OrderController(private val call: ApplicationCall) {
    private val logger = LoggerFactory.getLogger(OrderController::class.java)

    suspend fun addOrder() {
        val requestBody = try {
            call.receiveText()
        } catch (e: Exception) {
            logger.error("Failed to read request body: ${e.localizedMessage}")
            call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.localizedMessage}")
            return
        }

        val orderReceiveRemote = try {
            Json.decodeFromString<OrdersDTO>(requestBody)
        } catch (e: Exception) {
            logger.error("Failed to parse request: ${e.localizedMessage}")
            logger.error("Request body: $requestBody")
            call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.localizedMessage}")
            return
        }

        try {
            transaction {
                Orders.insert {
                    it[orderNum] = orderReceiveRemote.orderNum
                    it[userIdOrders] = orderReceiveRemote.userId
                    it[itemId_orders] = orderReceiveRemote.itemId
                    it[dateOrder] = orderReceiveRemote.dateOrder
                    it[addressOrder] = orderReceiveRemote.addressOrder
                    it[numberPhoneOrder] = orderReceiveRemote.numberPhoneOrder
                }
            }
            call.respond(HttpStatusCode.OK, "Order added successfully")
        } catch (e: Exception) {
            logger.error("Failed to add order: ${e.localizedMessage}")
            call.respond(HttpStatusCode.BadRequest, "Failed to add order: ${e.localizedMessage}")
        }
    }

    suspend fun removeOrder() {
        val requestBody = try {
            call.receiveText()
        } catch (e: Exception) {
            logger.error("Failed to read request body: ${e.localizedMessage}")
            call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.localizedMessage}")
            return
        }

        val orderReceiveRemote = try {
            Json.decodeFromString<OrdersDTO>(requestBody)
        } catch (e: Exception) {
            logger.error("Failed to parse request: ${e.localizedMessage}")
            logger.error("Request body: $requestBody")
            call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.localizedMessage}")
            return
        }

        try {
            transaction {
                Orders.deleteWhere {
                    (Orders.idOrder eq (orderReceiveRemote.idOrder ?: error("Order ID is required")))
                }
            }
            call.respond(HttpStatusCode.OK, "Order removed successfully")
        } catch (e: Exception) {
            logger.error("Failed to remove order: ${e.localizedMessage}")
            call.respond(HttpStatusCode.BadRequest, "Failed to remove order: ${e.localizedMessage}")
        }
    }

    suspend fun getUserOrders(userId: Int) {
        try {
            val userOrders = transaction {
                Orders.select { Orders.userIdOrders eq userId }
                    .map {
                        OrdersDTO(
                            idOrder = it[Orders.idOrder],
                            orderNum = it[Orders.orderNum],
                            userId = it[Orders.userIdOrders],
                            itemId = it[Orders.itemId_orders],
                            dateOrder = it[Orders.dateOrder],
                            addressOrder = it[Orders.addressOrder],
                            numberPhoneOrder = it[Orders.numberPhoneOrder]
                        )
                    }
            }
            call.respond(userOrders)
        } catch (e: Exception) {
            logger.error("Failed to fetch user orders", e)
            call.respond(HttpStatusCode.InternalServerError, "Failed to fetch user orders: ${e.localizedMessage}")
        }
    }
}