package ru.gadjetzona.features.basket

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.gadjetzona.database.basket.BasketDTO
import ru.gadjetzona.database.baskets.Basket
import ru.gadjetzona.database.basket.BasketItemDTO
import ru.gadjetzona.database.image.Image
import ru.gadjetzona.database.item.Item

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

    suspend fun getUserBasketItems(userId: Int) {
        try {
            val basketItems = transaction {
                (Basket innerJoin Item innerJoin Image)
                    .slice(
                        Item.itemId,
                        Item.name,
                        Item.price,
                        Basket.amount,
                        Image.dataImage
                    )
                    .select { Basket.userIdBasket eq userId }
                    .map {
                        BasketItemDTO(
                            itemId = it[Item.itemId],
                            itemName = it[Item.name],
                            price = it[Item.price],
                            quantity = it[Basket.amount] ?: 1,
                            imageData = it[Image.dataImage]
                        )
                    }
            }
            call.respond(basketItems)
        } catch (e: Exception) {
            logger.error("Failed to fetch user basket items", e)
            call.respond(HttpStatusCode.InternalServerError, "Failed to fetch user basket items: ${e.localizedMessage}")
        }
    }
}
