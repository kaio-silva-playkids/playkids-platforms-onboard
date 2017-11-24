package server.routes.user

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import com.movile.kotlin.commons.ktor.put
import io.ktor.http.HttpStatusCode
import server.domain.models.User
import server.services.UserService

fun Route.user(userService: UserService) {

    route("user") {


        get("{id}") {
            val id = call.parameters["id"]!!
            val entity = userService.find(id.toInt())

            if(entity != null)
                call.respond(entity.asUser())
            else
                call.respond(HttpStatusCode.NotFound)
        }

        put<User> { user -> userService.create(user)
            call.respond(HttpStatusCode.Created)
        }

    }
}