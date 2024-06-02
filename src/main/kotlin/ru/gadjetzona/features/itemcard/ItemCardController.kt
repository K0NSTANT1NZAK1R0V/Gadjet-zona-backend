package ru.gadjetzona.features.itemcard


import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.gadjetzona.database.image.Image
import ru.gadjetzona.database.item.Item
import ru.gadjetzona.database.item.ItemCardDTO


class ItemCardController(private val call: ApplicationCall) {
    private val logger = LoggerFactory.getLogger(ItemCardController::class.java)

    suspend fun getItemById(itemId: Int) {
        val itemcard = transaction {
            (Item innerJoin Image)
                .slice(
                    Item.itemId,
                    Image.dataImage,
                    Item.name,
                    Item.price,
                    Item.descrip,
                    Item.rating
                )
                .select { Item.itemId eq itemId }
                .map {
                    ItemCardDTO(
                        itemId = it[Item.itemId],
                        imageData = it[Image.dataImage], // Предполагая, что здесь хранится изображение товара
                        name = it[Item.name],
                        price = it[Item.price],
                        descrip = it[Item.descrip],
                        rating = it[Item.rating]
                    )
                }
        }
        call.respond(itemcard)
    }
}
