package ru.gadjetzona.database.likes

import kotlinx.serialization.Serializable


@Serializable
data class LikesItemDTO(
    val itemId: Int,
    val itemName: String,
    val price: Double,
    val rating: Double,
    val imageData: Int
)
