package ru.gadjetzona.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table() {
    val id_token = varchar("id", 50)
    val email_token = varchar("email", 100)
    val tokens = varchar("token", 50)

    fun insertToken(tokenDTO: TokenDTO) {
        transaction {
            insert {
                it[id_token] = tokenDTO.id_token
                it[email_token] = tokenDTO.email_token
                it[tokens] = tokenDTO.tokens
            }
        }
    }
}

