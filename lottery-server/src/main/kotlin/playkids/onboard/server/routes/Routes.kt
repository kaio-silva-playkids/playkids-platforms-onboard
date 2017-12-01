package playkids.onboard.server.routes

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import playkids.onboard.server.routes.account.account
import playkids.onboard.server.routes.authentication.authentication
import playkids.onboard.server.routes.lottery.lottery
import playkids.onboard.server.routes.ticket.ticket
import playkids.onboard.server.services.AuthenticationService
import playkids.onboard.server.services.LotteryService
import playkids.onboard.server.services.TicketService
import playkids.onboard.server.services.UserService
import server.routes.account.user


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