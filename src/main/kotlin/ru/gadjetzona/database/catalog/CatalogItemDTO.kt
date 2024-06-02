package ru.gadjetzona.database.catalog

import kotlinx.serialization.Serializable

@Serializable
data class CatalogItemDTO(
    val itemId: Int,
    val typeName: String,
    val name: String,
    val price: Double,
    val rating: Double,
    val imageData: Int
)