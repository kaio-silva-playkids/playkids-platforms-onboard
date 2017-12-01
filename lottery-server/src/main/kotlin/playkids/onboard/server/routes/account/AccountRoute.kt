package playkids.onboard.server.routes.account

import com.movile.kotlin.commons.ktor.post
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.route
import playkids.onboard.server.Server
import playkids.onboard.server.models.User
import playkids.onboard.server.services.UserService

fun Route.account(userService: UserService) {

    route("account") {

        post<User> { user ->
            val user = userService.create(user)

            if(user != null)
                call.respond(HttpStatusCode.Created, user)
            else
                call.respond(HttpStatusCode.InternalServerError)
        }

    }
}

