package server.domain.relationships

import org.jetbrains.exposed.sql.Table
import server.domain.models.Lotteries
import server.domain.models.Tickets

@Deprecated(message = "unused")
object LotteriesTickets : Table (name = "lotteries_tickets") {

    val lottery = reference("lottery", Lotteries).primaryKey(0)
    val ticket = reference("ticket", Tickets).primaryKey(1)

}