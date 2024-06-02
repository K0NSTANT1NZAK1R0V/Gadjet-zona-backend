package ru.gadjetzona.database.item

import kotlinx.serialization.Serializable

@Serializable
data class ItemDTO(
    val itemId: Int,
    val typeId: Int,
    val imageId: Int,
    val name: String,
    val price: Double,
    val descrip: String,
    val rating: Double
)
