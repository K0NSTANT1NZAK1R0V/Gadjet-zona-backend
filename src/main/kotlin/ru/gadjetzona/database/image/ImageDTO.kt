package ru.gadjetzona.database.image

import kotlinx.serialization.Serializable

@Serializable
data class ImageDTO(
    val imageId: Int,
    val nameImage: String,
    val dataImage: Int
)