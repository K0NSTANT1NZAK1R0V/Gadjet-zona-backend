package ru.gadjetzona.features.catalog

import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.gadjetzona.database.CatalogItemDTO
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
                    Item.descrip,
                    Item.rating,
                    Image.nameImage,
                    Image.dataImage
                )
                .selectAll()
                .map {
                    CatalogItemDTO(
                        itemId = it[Item.itemId],
                        typeName = it[Types.nameTypes],
                        name = it[Item.name],
                        price = it[Item.price],
                        description = it[Item.descrip],
                        rating = it[Item.rating],
                        imageName = it[Image.nameImage],
                        imageData = it[Image.dataImage]
                    )
                }
        }

        call.respond(catalogItems)
    }
}