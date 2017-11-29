package server.schedule

import server.Server
import server.domain.models.Lottery
import server.domain.models.LotteryEntity
import server.services.LotteryService
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