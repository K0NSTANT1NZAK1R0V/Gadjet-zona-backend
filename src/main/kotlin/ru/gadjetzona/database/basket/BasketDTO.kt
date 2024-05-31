package ru.gadjetzona.database.basket

data class BasketDTO(
    val basketId: Int? = null,
    val userId: Int,
    val itemId: Int? = null,
    val amount: Int? = null
)
