package ru.gadjetzona.database.types

import org.jetbrains.exposed.sql.Table


object Types: Table("types") {
    val typeId = integer("typeid").autoIncrement()
    val nameTypes = varchar("nametypes", 255)

    override val primaryKey = PrimaryKey(typeId, name = "PK_TYPE_ID")
}