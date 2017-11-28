package server.services

import org.jetbrains.exposed.sql.transactions.transaction
import server.domain.models.Lotteries
import server.domain.models.Lottery
import server.domain.models.LotteryEntity


class LotteryService {

    fun create(lottery: Lottery) {

        require(lottery.draw.isAfterNow)

        transaction {
            if(!LotteryEntity.find { Lotteries.id eq lottery.id}.empty()) {
                // TODO Raise 409 (Conflict)
                throw Exception("Lottery '${lottery.id}' already exists")
            }

            LotteryEntity.new {
                award = lottery.award
                price = lottery.price
                draw = lottery.draw
            }
        }
    }

    fun find(id: Int): LotteryEntity? = transaction { return@transaction LotteryEntity.findById(id) }

    fun all(): List<Lottery>? = transaction {
        return@transaction LotteryEntity.all().map { lotteryEntity -> lotteryEntity.asLottery() }.toList()
    }

}