package server.configuration

import server.services.AuthenticationService
import server.services.LotteryService
import server.services.TicketService
import server.services.UserService
import server.configuration.ClockConfiguration.clock
import server.configuration.ServerConfiguration.config

object ServicesConfiguration {

    val userService = UserService()
    val lotteryService = LotteryService()
    val ticketService = TicketService()
    val authenticationService = AuthenticationService(userService, clock, config.getString("secret"))

}