package ru.gadjetzona.database.item

import io.ktor.http.*
import org.jetbrains.exposed.sql.Table
import ru.gadjetzona.database.image.Image
import ru.gadjetzona.database.types.Types


object Item : Table ("item") {
    val itemId = integer("itemid").autoIncrement()
    val typeId = integer("typesid").references(Types.typeId)
    val name = varchar("name", 255 )
    val price = double("price")
    val descrip = varchar("descrip", 3000)
    val rating = double("reiting")
    val imageId = integer("imageid").references(Image.imageId)

    override val primaryKey = PrimaryKey(itemId, name = "PK_ITEM_ID")
}