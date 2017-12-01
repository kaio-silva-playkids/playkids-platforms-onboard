package playkids.onboard.server.routes.lottery

import com.movile.kotlin.commons.ktor.put
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import playkids.onboard.server.security.authenticate
import playkids.onboard.server.services.AuthenticationService
import playkids.onboard.server.services.LotteryService

fun Route.lottery(lotteryService: LotteryService, authenticationService: AuthenticationService) {

    route("lottery") {

        authenticate(authenticationService)

        get {
            val lotteries = lotteryService.all()

            if(lotteries != null)
                call.respond(lotteries.map { lotteryEntity -> lotteryEntity.fetch {
                    lotteryEntity.tickets = lotteryEntity.ticketEntities.map { t -> t.asTicket() }.toList()
                    lotteryEntity.winner = lotteryEntity.winnerEntity?.asUser()
                }.asLottery() })
            else
                call.respond(HttpStatusCode.NoContent)
        }

        get("{id}") {
            val id = call.parameters["id"]!!
            val entity = lotteryService.find(id.toInt())

            if(entity != null)
                call.respond(entity.asLottery())
            else
                call.respond(HttpStatusCode.NotFound)
        }

        put<playkids.onboard.server.models.Lottery> { lottery -> lotteryService.create(lottery)
            call.respond(HttpStatusCode.Created)
        }

    }
}