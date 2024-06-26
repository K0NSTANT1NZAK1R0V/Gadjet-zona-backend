package ru.gadjetzona.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.gadjetzona.database.tokens.TokenDTO
import ru.gadjetzona.database.tokens.Tokens
import ru.gadjetzona.database.users.Users
import java.util.*

class LoginController(private val call: ApplicationCall) {
        suspend fun performLogin() {
            val receive = call.receive<LoginRecieveRemote>()
            val userDTO = Users.fetchUser(receive.email)

            if (userDTO == null) {
                call.respond(HttpStatusCode.BadRequest, "User not found")
            } else {
                if (userDTO.password == receive.password) {
                    val token = UUID.randomUUID().toString()
                    Tokens.insertToken(
                        TokenDTO(
                            id_token = UUID.randomUUID().toString(),
                            email_token = receive.email,
                            tokens = token
                        )
                    )

                    call.respond(LoginResponseRemote(token = token))
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid password")
                }
            }
        }
    }
