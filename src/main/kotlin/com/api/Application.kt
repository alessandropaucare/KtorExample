package com.api

import com.api.plugins.*
import com.api.security.configureSecurity
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    embeddedServer(Netty,port = 8080, host = "0.0.0"){
        configureSerialization()
        configureSecurity()
        configureRouting()
    }.start(wait = true)

}
