package ru.gadjetzona.features.addtobasket

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import ru.gadjetzona.database.basket.BasketDTO
import ru.gadjetzona.database.baskets.Basket
import org.slf4j.LoggerFactory

class BasketController(private val call: ApplicationCall) {
    private val logger = LoggerFactory.getLogger(BasketController::class.java)

    suspend fun addItemToBasket() {
        val requestBody = try {
            call.receiveText()
        } catch (e: Exception) {
            logger.error("Failed to read request body: ${e.localizedMessage}")
            call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.localizedMessage}")
            return
        }

        val basketReceiveRemote = try {
            Json.decodeFromString<BasketDTO>(requestBody)
        } catch (e: Exception) {
            logger.error("Failed to parse request: ${e.localizedMessage}")
            logger.error("Request body: $requestBody")
            call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.localizedMessage}")
            return
        }

        try {
            transaction {
                Basket.insert {
                    it[userIdBasket] = basketReceiveRemote.userId
                    it[itemIdBasket] = basketReceiveRemote.itemId ?: error("Item ID is required")
                    it[amount] = basketReceiveRemote.amount ?: 1
                }
            }
            call.respond(HttpStatusCode.OK, "Item added to basket successfully")
        } catch (e: Exception) {
            logger.error("Failed to add item to basket: ${e.localizedMessage}")
            call.respond(HttpStatusCode.BadRequest, "Failed to add item to basket: ${e.localizedMessage}")
        }
    }

    suspend fun removeItemFromBasket() {
        val requestBody = try {
            call.receiveText()
        } catch (e: Exception) {
            logger.error("Failed to read request body: ${e.localizedMessage}")
            call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.localizedMessage}")
            return
        }

        val basketReceiveRemote = try {
            Json.decodeFromString<BasketDTO>(requestBody)
        } catch (e: Exception) {
            logger.error("Failed to parse request: ${e.localizedMessage}")
            logger.error("Request body: $requestBody")
            call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.localizedMessage}")
            return
        }

        try {
            transaction {
                Basket.deleteWhere {
                    (Basket.userIdBasket eq basketReceiveRemote.userId) and
                            (Basket.itemIdBasket eq (basketReceiveRemote.itemId ?: error("Item ID is required")))
                }
            }
            call.respond(HttpStatusCode.OK, "Item removed from basket successfully")
        } catch (e: Exception) {
            logger.error("Failed to remove item from basket: ${e.localizedMessage}")
            call.respond(HttpStatusCode.BadRequest, "Failed to remove item from basket: ${e.localizedMessage}")
        }
    }
}
