package playkids.onboard.server.schedule

import playkids.onboard.server.models.Lottery
import playkids.onboard.server.models.LotteryEntity
import playkids.onboard.server.services.LotteryService
import java.util.*
import kotlin.concurrent.timerTask

open class Schedule(private val lotteryService: LotteryService) {

    fun start(): Schedule {

        val lotteries: List<LotteryEntity>? = lotteryService.all();

        if(lotteries != null) {
            lotteries.forEach({lotteryEntity -> add(lotteryEntity.fetch {
                lotteryEntity.tickets = lotteryEntity.ticketEntities.map { t -> t.asTicket() }.toList()
            }.asLottery())});
        }

        return this
    }

    fun add(lottery: Lottery) {

        val timer = Timer()
        timer.schedule(timerTask {
            lotteryService.award(lottery);
        }, lottery.draw.toDate())

    }

}