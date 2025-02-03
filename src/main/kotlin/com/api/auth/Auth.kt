package com.api.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.api.security.AUDIENCE
import com.api.security.ISSUER
import com.api.security.SECRET_KEY
import java.util.Date


data class LoginRequest(
        val username: String,
        val password: String
    )
    data class TokenResponse(
        val token: String
    )
    fun createToken(username: String): String = JWT.create()
        .withAudience(AUDIENCE)
        .withIssuer(ISSUER)
        .withClaim("username", username)
        .withExpiresAt(Date(System.currentTimeMillis() + 60000 * 60 * 24))
        .sign(Algorithm.HMAC256(SECRET_KEY))