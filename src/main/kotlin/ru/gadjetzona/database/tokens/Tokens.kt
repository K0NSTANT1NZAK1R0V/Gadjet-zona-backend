package ru.gadjetzona.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ru.gadjetzona.database.users.UserDTO

object Tokens: Table() {
    private val id = Tokens.varchar("id", 50)
    private val email = Tokens.varchar("email", 25)
    private val token = Tokens.varchar("token", 50)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[id] = tokenDTO.id
                it[email] = tokenDTO.email
                it[token] = tokenDTO.token
            }
        }
    }
}