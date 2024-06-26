package ru.gadjetzona.database.baskets

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import ru.gadjetzona.database.basket.BasketDTO
import ru.gadjetzona.database.item.Item
import ru.gadjetzona.database.users.Users

object Basket : Table("basket") {
    val basketId = integer("basketid").autoIncrement()
    val userIdBasket = integer("userid").references(Users.id)
    val itemIdBasket = integer("itemid").references(Item.itemId).nullable()
    val amount = integer("amount").nullable()

    override val primaryKey = PrimaryKey(basketId, name = "PK_BASKET_ID")

    fun insertAndGetId(basketDTO: BasketDTO) {
         transaction {
            insert {
                it[userIdBasket] = basketDTO.userId
                it[itemIdBasket] = basketDTO.itemId
                it[amount] = basketDTO.amount
            }
        }
    }
}