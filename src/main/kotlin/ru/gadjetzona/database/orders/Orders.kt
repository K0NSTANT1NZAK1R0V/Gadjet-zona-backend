package ru.gadjetzona.database.orders


import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import java.sql.Date
import org.jetbrains.exposed.sql.transactions.transaction
import ru.gadjetzona.database.item.Item
import ru.gadjetzona.database.users.Users


object Orders : Table("orders") {
    val idOrder = integer("orderId").autoIncrement()
    val orderNum = integer("orderNum").nullable()
    val userIdOrders = integer("userId").references(Users.id)
    val itemId_orders = integer("itemId").references(Item.itemId).nullable()
    val dateOrder = varchar("date", 8).nullable()
    val addressOrder = varchar("address", 255).nullable()
    val numberPhoneOrder = varchar("phNum", 11).nullable()

    override val primaryKey = PrimaryKey(idOrder, name = "PK_Order_ID")

    fun insertAndGetId(ordersDTO: OrdersDTO) {
        transaction {
            insert {
                it[orderNum] = ordersDTO.orderNum
                it[userIdOrders] = ordersDTO.userId
                it[itemId_orders] = ordersDTO.itemId
                it[dateOrder] = ordersDTO.dateOrder
                it[addressOrder] = ordersDTO.addressOrder
                it[numberPhoneOrder] = ordersDTO.numberPhoneOrder
            }
        }
    }
}