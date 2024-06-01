package ru.gadjetzona.database.basket

import kotlinx.serialization.Serializable

@Serializable
data class BasketDTO(
    val basketId: Int? = null,
    val userId: Int,
    val itemId: Int? = null,
    val amount: Int? = null
)
