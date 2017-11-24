package server.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import server.domain.models.User
import java.time.Clock
import java.time.Duration
import java.util.*

class AuthenticationService(val userService: UserService, val clock: Clock, tokenSecret: String) {

    private val jwtAlgorithm = Algorithm.HMAC512(tokenSecret)
    private val jwtVerifier = (JWT.require(jwtAlgorithm) as JWTVerifier.BaseVerification).build(JWTClock(clock))

    suspend fun authenticate(username: String, password: String): User? = userService.authenticate(username, password)?.asUser()

    suspend fun refreshToken(token: String): String? =
            decodeToken(token)
                    ?.let { userService.find(it.getClaim(Claims.USERNAME).asString()) }
                    ?.let { generateToken(it.asUser()) }


    fun decodeToken(token: String): DecodedJWT? =
            try {
                jwtVerifier.verify(token)
            } catch (exception: JWTVerificationException) {
                null
            }

    fun generateToken(user: User): String =
            JWT.create()
                    .withIssuer(JWT_ISSUER)
                    .withClaim(Claims.USERNAME, user.username)
                    .withClaim(Claims.EMAIL, user.email)
                    .withExpiresAt(Date(clock.instant().plus(TOKEN_TTL).toEpochMilli()))
                    .sign(jwtAlgorithm)

    companion object {
        private val JWT_ISSUER = "lottery"
        private val TOKEN_TTL = Duration.ofDays(30)

        object Claims {
            val USERNAME = "username"
            val EMAIL = "email"
        }
    }

}

class JWTClock(val clock: Clock) : com.auth0.jwt.interfaces.Clock {
    override fun getToday(): Date = Date(clock.millis())
}