package ru.gadjetzona.database.image

import org.jetbrains.exposed.sql.Table


object Image: Table ("image"){
    val imageId = integer("imageid")
    val nameImage = varchar("name",64)
    val dataImage = integer("data")

    override val primaryKey = PrimaryKey(imageId, name = "PK_IMAGE_ID")
}