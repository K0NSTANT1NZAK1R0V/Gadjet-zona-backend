package ru.gadjetzona.database.orders

import kotlinx.serialization.Serializable

@Serializable
data class OrdersItemDTO(
    val idOrder: Int,
    val orderNum: Int,
    val userId: Int,
    val itemId: Int,
    val itemName: String,
    val price: Double,
    val amount: Int,
    val imageData: Int,
    val dateOrder: String,
    val addressOrder: String,
    val numberPhoneOrder: String
)
