package server.services

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import server.domain.models.*

class TicketService {

    fun buy(userId: Int, lotteryId: Int) {

        transaction {

            val userEntity = UserEntity.findById(userId)
            val lotteryEntity = LotteryEntity.findById(lotteryId)

            if(userEntity != null && lotteryEntity != null) {

                if(userEntity.credit >= lotteryEntity.price) {

                    TicketEntity.new {
                        user = userEntity
                        lottery = lotteryEntity
                    }

                    Users.update({Users.id eq userEntity.id}) {
                        it[credit] = userEntity.credit - lotteryEntity.price
                    }
                } else
                    throw Exception("Insufficient credits")

            } else
                throw Exception("User or lottery invalid")
        }

    }

    fun find(id: Int): LotteryEntity? = transaction { return@transaction LotteryEntity.findById(id) }

}