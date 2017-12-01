package playkids.onboard.server.routes.ticket

import com.movile.kotlin.commons.ktor.post
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import playkids.onboard.server.models.Lottery
import playkids.onboard.server.security.authenticate
import playkids.onboard.server.security.securityContext
import playkids.onboard.server.services.AuthenticationService
import playkids.onboard.server.services.TicketService
import playkids.onboard.server.services.UserService

fun Route.ticket(ticketService: TicketService, userService: UserService, authenticationService: AuthenticationService) {

    route("ticket") {

        authenticate(authenticationService)

        get("{id}") {
            val id = call.parameters["id"]!!
            val entity = ticketService.find(id.toInt())

            if(entity != null)
                call.respond(entity.asLottery())
            else
                call.respond(HttpStatusCode.NotFound)
        }

        post<playkids.onboard.server.models.Lottery>("buy") { lottery ->

            val context = call.securityContext()
            var entity = userService.find(context.principal)

            if(entity != null) {
                val user = ticketService.buy(entity, lottery)

                if(user != null)
                    call.respond(HttpStatusCode.OK, user.fetch().asUser())
            } else
                call.respond(HttpStatusCode.Forbidden)
        }

    }
}