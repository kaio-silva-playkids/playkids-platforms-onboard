package server.routes.account

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import com.movile.kotlin.commons.ktor.post
import io.ktor.http.HttpStatusCode
import server.Server
import server.domain.models.User
import server.security.authenticate
import server.services.AuthenticationService
import server.services.UserService

fun Route.user(userService: UserService, authenticationService: AuthenticationService) {


    route("user") {

    authenticate(authenticationService)

        get("{id}") {
            val id = call.parameters["id"]!!
            val entity = userService.find(id.toInt())

            if(entity != null)
                call.respond(entity.asUser())
            else
                call.respond(HttpStatusCode.NotFound)
        }

    }
}