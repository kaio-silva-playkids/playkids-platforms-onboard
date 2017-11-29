package server.routes

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import server.routes.account.account
import server.routes.account.user
import server.routes.authentication.authentication
import server.routes.lottery.lottery
import server.routes.ticket.ticket
import server.services.AuthenticationService
import server.services.LotteryService
import server.services.TicketService
import server.services.UserService


fun Route.api(authenticationService: AuthenticationService,
              userService: UserService,
              lotteryService: LotteryService,
              ticketService: TicketService) {

    route("api") {

        account(userService)

        authentication(authenticationService)

        user(userService, authenticationService)

        lottery(lotteryService, authenticationService)

        ticket(ticketService, userService, authenticationService)

        get {
            call.respondText("Lottery Server API", ContentType.Text.Html)
        }

    }
}