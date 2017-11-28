package server.routes.authentication

import com.movile.kotlin.commons.ktor.post
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.route
import server.services.AuthenticationService

fun Route.authentication(authenticationService: AuthenticationService) {
    route("auth") {

        post<AuthenticationRequest> { (login, password) ->
            val user = authenticationService.authenticate(login, password)

            if (user != null) {
                call.respond(AuthenticationResponse(authenticationService.generateToken(user)))
            } else {
                call.respond(HttpStatusCode.Forbidden)
            }
        }

        post<RefreshTokenRequest>("refresh") { (oldToken) ->
            val token = authenticationService.refreshToken(oldToken)

            if (token != null) {
                call.respond(AuthenticationResponse(token))
            } else {
                call.respond(HttpStatusCode.Forbidden)
            }
        }

    }
}

data class AuthenticationRequest(val username: String, val password: String)
data class RefreshTokenRequest(val token: String)

data class AuthenticationResponse(val token: String)