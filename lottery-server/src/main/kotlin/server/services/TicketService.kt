package server.services

import org.jetbrains.exposed.sql.transactions.transaction
import server.domain.models.*

class TicketService {

    fun buy(userId: Int, lotteryId: Int) {

        transaction {

            val userEntity = UserEntity.findById(userId)
            val lotteryEntity = LotteryEntity.findById(lotteryId)

            if(userEntity != null && lotteryEntity != null) {
                TicketEntity.new {
                    user = userEntity
                    lottery = lotteryEntity
                }
            } else
                throw Exception("User or lottery invalid")
        }

    }

    fun find(id: Int): LotteryEntity? = transaction { return@transaction LotteryEntity.findById(id) }

}