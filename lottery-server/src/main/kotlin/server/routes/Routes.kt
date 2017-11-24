package server.routes

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import server.routes.authentication.authentication
import server.routes.lottery.lottery
import server.routes.ticket.ticket
import server.routes.user.user
import server.services.AuthenticationService
import server.services.LotteryService
import server.services.TicketService
import server.services.UserService


fun Route.api(authenticationService: AuthenticationService,
              userService: UserService,
              lotteryService: LotteryService,
              ticketService: TicketService) {

    route("api") {

        authentication(authenticationService)

        user(userService)

        lottery(lotteryService)

        ticket(ticketService)

        get {
            call.respondText("Lottery Server API", ContentType.Text.Html)
        }

    }
}