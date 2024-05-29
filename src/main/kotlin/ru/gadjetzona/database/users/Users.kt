package ru.gadjetzona.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table("users") {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 100).uniqueIndex()
    val username = varchar("username", 25)
    val password = varchar("password", 100)
    val phonenumber = varchar("phonenumber", 11).nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_User_ID")

    fun insert(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[email] = userDTO.email
                it[password] = userDTO.password
                it[username] = userDTO.username ?: ""
                it[phonenumber] = userDTO.phonenumber ?: ""
            }
        }
    }

    fun fetchUser(email: String): UserDTO? {
        return try {
            transaction {
                Users.select { Users.email eq email }
                    .mapNotNull {
                        UserDTO(
                            id = it[Users.id],
                            email = it[Users.email],
                            password = it[Users.password],
                            username = it[Users.username],
                            phonenumber = it[Users.phonenumber]
                        )
                    }
                    .singleOrNull()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun fetchUserById(id: Int): UserDTO? {
        return try {
            transaction {
                Users.select { Users.id eq id }
                    .mapNotNull {
                        UserDTO(
                            id = it[Users.id],
                            email = it[Users.email],
                            password = it[Users.password],
                            username = it[Users.username],
                            phonenumber = it[Users.phonenumber]
                        )
                    }
                    .singleOrNull()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
