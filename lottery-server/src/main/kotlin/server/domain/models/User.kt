package server.domain.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction

object Users: IntIdTable() {
    val username = varchar("username", length = 50)
    val email = varchar("email", length = 50)
    val credit = integer("credit")
    val password = text(name = "password")
}

data class User(var id: Int?, var username: String, var email: String,
                var password: String, var credit: Int, var tickets: List<Ticket>?) {

    init {
        require(username.isNotEmpty())
        require(email.isNotEmpty())
        require(password.isNotEmpty())
    }
}

class UserEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<UserEntity>(Users)

    var username by Users.username
    var email by Users.email
    var credit by Users.credit
    var password by Users.password
    private val ticketEntities by TicketEntity referrersOn Tickets.user
    var tickets: List<Ticket>? = null

    fun asUser(): User = User(id.value, username, email, password, credit, tickets)

    fun fetch(): UserEntity {
        transaction {
            tickets = ticketEntities.map { t -> t.asTicket() }.toList()
        }
        return this
    }

}