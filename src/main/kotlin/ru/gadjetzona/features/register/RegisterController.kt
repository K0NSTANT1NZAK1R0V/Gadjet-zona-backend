package ru.gadjetzona.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.transactions.transaction
import ru.gadjetzona.database.tokens.TokenDTO
import ru.gadjetzona.database.tokens.Tokens
import ru.gadjetzona.database.users.UserDTO
import ru.gadjetzona.database.users.Users
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

                // Вставка нового пользователя
                Users.insert(
                    UserDTO(
                        email = registerReceiveRemote.email,
                        password = registerReceiveRemote.password,
                        username = registerReceiveRemote.username,
                        phonenumber = registerReceiveRemote.phonenumber
                    )
                )

                // Вставка токена
                Tokens.insert(
                    TokenDTO(
                        id = UUID.randomUUID().toString(),
                        email = registerReceiveRemote.email,
                        token = token
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
