package ru.gadjetzona.database.orders

import kotlinx.serialization.Serializable

@Serializable
data class OrdersDTO(
    val idOrder: Int? = null,
    val orderNum: Int,
    val userId: Int,
    val amount: Int? = null,
    val itemId: Int? = null,
    val dateOrder: String? = null,
    val addressOrder: String? = null,
    val numberPhoneOrder: String? = null
)
