package ru.gadjetzona.features.addtolikes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.gadjetzona.database.likes.Likes
import ru.gadjetzona.database.likes.LikesDTO


class LikesController(private val call: ApplicationCall) {
    private val logger = LoggerFactory.getLogger(LikesController::class.java)

    suspend fun addItemToLikes() {
        val requestBody = try {
            call.receiveText()
        } catch (e: Exception) {
            logger.error("Failed to read request body: ${e.localizedMessage}")
            call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.localizedMessage}")
            return
        }

        val likesReceiveRemote = try {
            Json.decodeFromString<LikesDTO>(requestBody)
        } catch (e: Exception) {
            logger.error("Failed to parse request: ${e.localizedMessage}")
            logger.error("Request body: $requestBody")
            call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.localizedMessage}")
            return
        }

        try {
            transaction {
                Likes.insert {
                    it[userIdLikes] = likesReceiveRemote.userId
                    it[itemId] = likesReceiveRemote.itemId ?: error("Item ID is required")
                    it[amount] = likesReceiveRemote.amount ?: 1
                }
            }
            call.respond(HttpStatusCode.OK, "Item added to likes successfully")
        } catch (e: Exception) {
            logger.error("Failed to add item to basket: ${e.localizedMessage}")
            call.respond(HttpStatusCode.BadRequest, "Failed to add item to likes: ${e.localizedMessage}")
        }
    }

    suspend fun removeItemFromLikes() {
        val requestBody = try {
            call.receiveText()
        } catch (e: Exception) {
            logger.error("Failed to read request body: ${e.localizedMessage}")
            call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.localizedMessage}")
            return
        }

        val likesReceiveRemote = try {
            Json.decodeFromString<LikesDTO>(requestBody)
        } catch (e: Exception) {
            logger.error("Failed to parse request: ${e.localizedMessage}")
            logger.error("Request body: $requestBody")
            call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.localizedMessage}")
            return
        }

        try {
            transaction {
                Likes.deleteWhere {
                    (Likes.userIdLikes eq likesReceiveRemote.userId) and
                            (Likes.itemId eq (likesReceiveRemote.itemId ?: error("Item ID is required")))
                }
            }
            call.respond(HttpStatusCode.OK, "Item removed from likes successfully")
        } catch (e: Exception) {
            logger.error("Failed to remove item from likes: ${e.localizedMessage}")
            call.respond(HttpStatusCode.BadRequest, "Failed to remove item from likes: ${e.localizedMessage}")
        }
    }
}