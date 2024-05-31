package ru.gadjetzona.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.gadjetzona.database.tokens.TokenDTO
import ru.gadjetzona.database.tokens.Tokens
import ru.gadjetzona.database.users.UserDTO
import ru.gadjetzona.database.users.Users
import ru.gadjetzona.database.basket.BasketDTO
import ru.gadjetzona.database.baskets.Basket
import ru.gadjetzona.database.likes.Likes
import ru.gadjetzona.database.likes.LikesDTO
import ru.gadjetzona.database.orders.Orders
import ru.gadjetzona.database.orders.OrdersDTO
import ru.gadjetzona.utils.isValidEmail
import java.util.*

class RegisterController(val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterRecieveRemote>()

        // Проверка на валидность email
        if (!registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
            return
        }

        try {
            val token = UUID.randomUUID().toString()

            transaction {
                // Проверка существования пользователя по email
                val userByEmail = Users.fetchUser(registerReceiveRemote.email)
                if (userByEmail != null) {
                    throw IllegalArgumentException("User with this email already exists")
                }

                // Вставка нового пользователя и получение его ID
                val userId = Users.insertAndGetId(
                    UserDTO(
                        email = registerReceiveRemote.email,
                        password = registerReceiveRemote.password,
                        username = registerReceiveRemote.username,
                        phonenumber = registerReceiveRemote.phonenumber
                    )
                )

                // Вставка связанных записей
                Basket.insertAndGetId(
                    BasketDTO(
                        userId = userId,
                        itemId = null,
                        amount = null
                    )
                )
                Likes.insertAndGetId(
                    LikesDTO(
                        userId = userId,
                        itemId = null,
                        amount = null
                    )
                )
                Orders.insertAndGetId(
                    OrdersDTO(
                        userId = userId,
                        orderNum = null,
                        itemId = null,
                        dateOrder = null,
                        addressOrder = null,
                        numberPhoneOrder = null
                    )
                )

                // Вставка токена
                Tokens.insertToken(
                    TokenDTO(
                        id_token = UUID.randomUUID().toString(),
                        email_token = registerReceiveRemote.email,
                        tokens = token
                    )
                )
            }

            call.respond(RegisterResponseRemote(token = token))
        } catch (e: IllegalArgumentException) {
            call.respond(HttpStatusCode.Conflict, e.message ?: "User already exists")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Can't create user: ${e.localizedMessage}")
        }
    }
}
