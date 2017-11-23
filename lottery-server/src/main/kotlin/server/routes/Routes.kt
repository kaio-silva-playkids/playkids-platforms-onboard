package server.routes

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import server.routes.lottery.ticket
import server.routes.user.user
import server.services.UserService


fun Route.api(userService: UserService) {

    route("api") {

        ticket()

        user(userService)

        get {
            call.respondText("Lottery Server API", ContentType.Text.Html)
        }

    }
}