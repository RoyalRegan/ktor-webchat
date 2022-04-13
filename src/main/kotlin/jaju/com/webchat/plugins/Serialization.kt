package jaju.com.webchat.plugins

import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.thymeleaf.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
            ignoreType<ThymeleafContent>()
        }
    }
}
