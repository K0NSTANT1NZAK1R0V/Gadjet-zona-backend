package ru.gadjetzona.database.likes

import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import ru.gadjetzona.database.item.Item
import ru.gadjetzona.database.users.Users

object Likes : Table("likes") {
    val likesId = integer("likesid").autoIncrement()
    val userIdLikes = integer("userid").references(Users.id)
    val itemId = integer("itemid").references(Item.itemId).nullable()
    val amount = integer("amount").nullable()

    override val primaryKey = PrimaryKey(likesId, name = "PK_LIKES_ID")

    fun insertAndGetId(likesDTO: LikesDTO) {
        transaction {
            insert {
                it[userIdLikes] = likesDTO.userId
                it[itemId] = likesDTO.itemId
                it[amount] = likesDTO.amount
            }
        }
    }
}
