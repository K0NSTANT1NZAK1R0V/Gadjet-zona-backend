package ru.gadjetzona.database.characteristiks

import org.jetbrains.exposed.sql.Table
import ru.gadjetzona.database.types.Types

object Characteristics: Table("characteristics") {
    val charId = integer("charId").autoIncrement()
    val typeId = integer("typeId").references(Types.typeId)
    val label = varchar("label", 255)

    override val primaryKey = PrimaryKey(charId, name = "PK_CHARACTERISTICS_ID")
}