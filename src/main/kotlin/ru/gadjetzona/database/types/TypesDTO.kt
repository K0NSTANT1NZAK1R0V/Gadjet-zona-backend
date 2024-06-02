package ru.gadjetzona.database.types

import kotlinx.serialization.Serializable


@Serializable
data class TypesDTO(
    val typeId: Int,
    val nameTypes: String
)
