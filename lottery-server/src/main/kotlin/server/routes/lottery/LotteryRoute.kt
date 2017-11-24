package server.routes.lottery

import com.movile.kotlin.commons.ktor.put
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import server.domain.models.Lottery
import server.services.LotteryService

fun Route.lottery(lotteryService: LotteryService) {

    route("lottery") {

        get("{id}") {
            val id = call.parameters["id"]!!
            val entity = lotteryService.find(id.toInt())

            if(entity != null)
                call.respond(entity.asLottery())
            else
                call.respond(HttpStatusCode.NotFound)
        }

        put<Lottery> { lottery -> lotteryService.create(lottery)
            call.respond(HttpStatusCode.Created)
        }

    }
}