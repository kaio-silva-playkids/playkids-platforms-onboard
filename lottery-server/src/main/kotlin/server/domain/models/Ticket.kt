package server.domain.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Tickets: IntIdTable() {
    val user = reference("user", Users)
}

class Ticket(id: EntityID<Int>): IntEntity(id) {

    companion object : IntEntityClass<Ticket>(Tickets)

    val user by Tickets.user

}