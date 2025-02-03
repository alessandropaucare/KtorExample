package com.api.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

    const val SECRET_KEY = "tu_clave_secreta_muy_larga_y_segura"
    const val ISSUER = "tu_aplicacion"
    const val AUDIENCE = "tu_api"
    private const val REALM = "acceso_api"

    fun Application.configureSecurity() {
        authentication {
            jwt {
                realm = REALM
                verifier(
                    JWT.require(Algorithm.HMAC256(SECRET_KEY))
                        .withAudience(AUDIENCE)
                        .withIssuer(ISSUER)
                        .build()
                )
                validate { credential ->
                    if (credential.payload.audience.contains(AUDIENCE)) {
                        JWTPrincipal(credential.payload)
                    } else null
                }
            }
        }
    }