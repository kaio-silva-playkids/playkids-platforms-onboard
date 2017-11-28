package server.routes.account

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import server.security.authenticate
import server.security.securityContext
import server.services.AuthenticationService
import server.services.UserService

fun Route.user(userService: UserService, authenticationService: AuthenticationService) {

    route("user") {

    authenticate(authenticationService)

        get("profile") {
            val context = call.securityContext()
            val entity = userService.find(context.principal)

            if(entity != null) {
                call.respond(entity.fetch().asUser())
            } else
                call.respond(HttpStatusCode.NotFound)
        }
    }
}