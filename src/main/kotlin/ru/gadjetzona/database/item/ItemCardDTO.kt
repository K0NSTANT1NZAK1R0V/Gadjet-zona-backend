package ru.gadjetzona.database.item

import kotlinx.serialization.Serializable

@Serializable
data class ItemCardDTO(
    val itemId: Int,
    val imageData: Int,
    val name: String,
    val price: Double,
    val descrip: String,
    val rating: Double
)
