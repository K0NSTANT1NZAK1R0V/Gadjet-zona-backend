package ru.gadjetzona.database.likes

data class LikesDTO(
    val likesId: Int? = null,
    val userId: Int,
    val itemId: Int? = null,
    val amount: Int? = null
)
