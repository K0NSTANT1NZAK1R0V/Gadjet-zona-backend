package ru.gadjetzona.database.basket

import kotlinx.serialization.Serializable


@Serializable
data class BasketItemDTO(
    val itemId: Int,
    val itemName: String,
    val price: Double,
    val quantity: Int,
    val imageData: Int
)
