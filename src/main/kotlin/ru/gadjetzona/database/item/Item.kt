package ru.gadjetzona.database.item

import org.jetbrains.exposed.sql.Table
import ru.gadjetzona.database.characteristiks.Characteristics.references
import ru.gadjetzona.database.types.Types


object Item : Table ("item") {
    val itemId = integer("itemId").autoIncrement()
    val typeId = integer("typeId").references(Types.typeId)
    val nameTypes = varchar("nameTypes", 255)

    override val primaryKey = PrimaryKey(itemId, name = "PK_ITEM_ID")
}