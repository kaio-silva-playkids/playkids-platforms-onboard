package server.domain.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Tickets: IntIdTable() {
    val user = reference("user", Users)
    val lottery = reference("lottery", Lotteries)
}

data class Ticket(var id: Int?, var user: User, var lottery: Lottery) {
    init {
        require(user.id != null)
        require(lottery.id != null)
    }

    fun asEntity(): TicketEntity = TicketEntity(EntityID(id, Tickets))
}

class TicketEntity(id: EntityID<Int>): IntEntity(id) {

    companion object : IntEntityClass<TicketEntity>(Tickets)

    var user by UserEntity referencedOn Tickets.user
    var lottery by LotteryEntity referencedOn Tickets.lottery

    //fun asTicket(): Ticket = Ticket(id.value)

}