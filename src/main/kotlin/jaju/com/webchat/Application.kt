package jaju.com.webchat

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import jaju.com.webchat.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureMonitoring()
        configureSerialization()
        configureSockets()
        configureTemplating()
    }.start(wait = true)
}
