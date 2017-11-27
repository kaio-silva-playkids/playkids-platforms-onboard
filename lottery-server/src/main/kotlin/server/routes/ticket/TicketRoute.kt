package server.routes.ticket

import com.movile.kotlin.commons.ktor.post
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import server.security.authenticate
import server.services.AuthenticationService
import server.services.TicketService

fun Route.ticket(ticketService: TicketService, authenticationService: AuthenticationService) {

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

        post<BuyTicketRequest>("buy") { request -> ticketService.buy(request.user, request.lottery)
            call.respond(HttpStatusCode.Created)
        }

    }
}

data class BuyTicketRequest(val user: Int, val lottery: Int)