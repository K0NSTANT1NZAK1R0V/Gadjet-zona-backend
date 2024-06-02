package ru.gadjetzona.database

import kotlinx.serialization.Serializable

@Serializable
data class CatalogItemDTO(
    val itemId: Int,
    val typeName: String,
    val name: String,
    val price: Double,
    val description: String,
    val rating: Double,
    val imageName: String,
    val imageData: Int
)