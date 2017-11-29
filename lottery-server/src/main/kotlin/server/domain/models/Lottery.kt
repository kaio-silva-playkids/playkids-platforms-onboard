package server.domain.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

object Lotteries: IntIdTable() {
    val award = integer("award")
    val price = integer("price")
    val draw = datetime("draw")
    val winner = reference("winner", Users).nullable()
}

data class Lottery(val id: Int?, var award: Int, var price: Int,
                   var draw: DateTime, val tickets: List<Ticket>?, val winner: User?) {
    init {
        require(price > 0)
        require(award > 0)
    }
}

class LotteryEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<LotteryEntity>(Lotteries)

    var award by Lotteries.award
    var price by Lotteries.price
    var draw by Lotteries.draw
    val ticketEntities by TicketEntity referrersOn Tickets.lottery
    var tickets: List<Ticket>? = null
    val winnerEntity by UserEntity optionalReferencedOn Lotteries.winner
    var winner: User? = null

    fun asLottery(): Lottery = Lottery(id.value, award, price, draw, tickets, winner)

    fun fetch(block: (lotteryEntity: LotteryEntity) -> Unit): LotteryEntity {
        transaction {
            block(this@LotteryEntity);
        }
        return this
    }
}