package ru.gadjetzona.database.types

import org.jetbrains.exposed.sql.Table


object Types: Table("types") {
    val typeId = integer("typeId").autoIncrement()
    val nameTypes = varchar("nameTypes", 255)

    override val primaryKey = PrimaryKey(typeId, name = "PK_TYPE_ID")
}