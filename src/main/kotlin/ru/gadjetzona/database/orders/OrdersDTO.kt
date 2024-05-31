package ru.gadjetzona.database.orders

import java.sql.Date
import java.time.LocalDateTime

data class OrdersDTO(
    val idOrder: Int? = null,
    val orderNum: Int? = null,
    val userId: Int,
    val itemId: Int? = null,
    val dateOrder: String? = null,
    val addressOrder: String? = null,
    val numberPhoneOrder: String? = null
)
