package ru.gadjetzona.database.collection

import org.jetbrains.exposed.sql.Table
import ru.gadjetzona.database.characteristiks.Characteristics
import ru.gadjetzona.database.item.Item

object Collection : Table("collection") {
    val collId = integer("collId").autoIncrement()
    val charId = integer("charId").references(Characteristics.charId)
    val itemId = integer("itemId").references(Item.itemId)
    val values = varchar("value",255)

    override val primaryKey = PrimaryKey(collId, name = "PK_COLLECTION_ID")
}