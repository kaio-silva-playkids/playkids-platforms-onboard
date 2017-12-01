package playkids.onboard.server.services

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import playkids.onboard.server.Server
import playkids.onboard.server.models.*
import java.util.*


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

    fun all(): List<LotteryEntity>? = transaction {
        return@transaction LotteryEntity.all().toList()
    }

    fun award(lottery: Lottery): Boolean {
        if(lottery.tickets != null && lottery.tickets.isNotEmpty()) {

            val index = (0..lottery.tickets.size).random()

            transaction {

                val user = UserEntity.findById(lottery.tickets.get(index).user.id!!)

                if(user != null) {

                    Lotteries.update({ Lotteries.id eq lottery.id }) {
                        it[winner] = EntityID(user.id.value, Users)
                    }

                    Users.update({ Users.id eq user.id.value }) {
                        Server.logger.info(it.toString())
                        it[credit] = lottery.award + user.credit
                    }
                } else
                    throw Exception("Invalid user");
            }
            return true
        }
        return false
    }

    fun ClosedRange<Int>.random() =
            Random().nextInt(endInclusive - start) +  start

}