package playkids.onboard.server.configuration

import playkids.onboard.server.configuration.ClockConfiguration.clock
import playkids.onboard.server.configuration.ServerConfiguration.config
import playkids.onboard.server.services.AuthenticationService
import playkids.onboard.server.services.LotteryService
import playkids.onboard.server.services.TicketService
import playkids.onboard.server.services.UserService

object ServicesConfiguration {

    val userService = UserService()
    val lotteryService = LotteryService()
    val ticketService = TicketService()
    val authenticationService = AuthenticationService(userService, clock, config.getString("secret"))

}