package ru.gadjetzona.cache

import ru.gadjetzona.features.register.RegisterRecieveRemote


data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache {
    val userList: MutableList<RegisterRecieveRemote> = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()
}