package server.services

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import server.domain.models.*

class TicketService {

    fun buy(buyer: UserEntity, desiredLottery: Lottery): UserEntity? {

        if(desiredLottery.id != null) {

            var updatedUser: UserEntity? = null

            transaction {

                val lotteryEntity = LotteryEntity.findById(desiredLottery.id)

                if(lotteryEntity != null) {

                    if(buyer.credit >= lotteryEntity.price) {

                        TicketEntity.new {
                            user = buyer
                            lottery = lotteryEntity
                        }

                        Users.update({Users.id eq buyer.id}) {
                            it[credit] = buyer.credit - lotteryEntity.price
                        }

                        updatedUser = UserEntity.findById(buyer.id)
                    } else
                        throw Exception("Insufficient credits")

                } else
                    throw Exception("User or lottery invalid")
            }

            return updatedUser
        }
        throw Exception("Something went wrong")
    }

    fun find(id: Int): LotteryEntity? = transaction { return@transaction LotteryEntity.findById(id) }

}