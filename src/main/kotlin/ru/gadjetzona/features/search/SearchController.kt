package ru.gadjetzona.features.search

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.gadjetzona.database.item.Item
import ru.gadjetzona.database.item.ItemDTO

class ItemController(private val call: ApplicationCall) {
    private val logger = LoggerFactory.getLogger(ItemController::class.java)

    suspend fun searchItems(query: String) {
        try {
            val items = transaction {
                Item.select {
                    Item.name like "%$query%" or
                            (Item.descrip like "%$query%")
                }.map {
                    ItemDTO(
                        itemId = it[Item.itemId],
                        typeId = it[Item.typeId],
                        name = it[Item.name],
                        price = it[Item.price],
                        descrip = it[Item.descrip],
                        rating = it[Item.rating],
                        imageId = it[Item.imageId]
                    )
                }
            }
            call.respond(items)
        } catch (e: Exception) {
            logger.error("Failed to search items", e)
            call.respond(HttpStatusCode.InternalServerError, "Failed to search items: ${e.localizedMessage}")
        }
    }
}