package ru.gadjetzona.features.catalog

import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.gadjetzona.database.catalog.CatalogItemDTO
import ru.gadjetzona.database.image.Image
import ru.gadjetzona.database.item.Item
import ru.gadjetzona.database.types.Types

class CatalogController(private val call: ApplicationCall) {
    private val logger = LoggerFactory.getLogger(CatalogController::class.java)

    suspend fun getCatalogItems() {
        val catalogItems = transaction {
            (Item innerJoin Types innerJoin Image)
                .slice(
                    Item.itemId,
                    Types.nameTypes,
                    Item.name,
                    Item.price,
                    Item.rating,
                    Image.dataImage
                )
                .selectAll()
                .map {
                    CatalogItemDTO(
                        itemId = it[Item.itemId],
                        typeName = it[Types.nameTypes],
                        name = it[Item.name],
                        price = it[Item.price],
                        rating = it[Item.rating],
                        imageData = it[Image.dataImage]
                    )
                }
        }

        call.respond(catalogItems)
    }

    suspend fun getCatalogItemsByCategory(typeId: Int) {
        val catalogItems = transaction {
            (Item innerJoin Types innerJoin Image)
                .slice(
                    Item.itemId,
                    Types.nameTypes,
                    Item.name,
                    Item.price,
                    Item.rating,
                    Image.dataImage
                )
                .select { Item.typeId eq typeId }
                .map {
                    CatalogItemDTO(
                        itemId = it[Item.itemId],
                        typeName = it[Types.nameTypes],
                        name = it[Item.name],
                        price = it[Item.price],
                        rating = it[Item.rating],
                        imageData = it[Image.dataImage]
                    )
                }
        }
        call.respond(catalogItems)
    }
}