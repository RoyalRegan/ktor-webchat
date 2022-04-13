package jaju.com.webchat

import io.ktor.server.application.*
import io.ktor.server.netty.*
import jaju.com.webchat.plugins.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureRouting()
    configureMonitoring()
    configureSerialization()
    configureSockets()
    configureTemplating()
}