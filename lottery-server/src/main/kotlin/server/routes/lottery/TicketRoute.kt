package server.routes.lottery

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.ticket() {

    route("ticket") {

        get {
            call.respondText("get tickets", ContentType.Text.Html)
        }

    }
}