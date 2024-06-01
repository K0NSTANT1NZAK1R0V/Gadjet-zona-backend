package ru.gadjetzona.database.likes

import kotlinx.serialization.Serializable

@Serializable
data class LikesDTO(
    val likesId: Int? = null,
    val userId: Int,
    val itemId: Int? = null,
    val amount: Int? = null
)
