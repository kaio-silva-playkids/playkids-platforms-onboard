package server.routes.account

import com.movile.kotlin.commons.ktor.post
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.route
import server.Server
import server.domain.models.User
import server.services.UserService

fun Route.account(userService: UserService) {

    route("account") {

        post<User> { user ->
            val user = userService.create(user)

            Server.logger.info(user.toString())

            if(user != null)
                call.respond(HttpStatusCode.Created, user)
            else
                call.respond(HttpStatusCode.InternalServerError)
        }

    }
}