package server.persistence

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import server.domain.models.Lotteries
import server.domain.models.Tickets
import server.domain.models.Users
import server.domain.relationships.LotteriesTickets

open class Database(private var configuration: Configuration) {

    fun start(): Database {

        val db = Database.connect(configuration.url(), configuration.driver, configuration.user, configuration.password)

        transaction {
            SchemaUtils.drop(Users, Lotteries, Tickets, LotteriesTickets)
            SchemaUtils.create(Users, Lotteries, Tickets)
        }

        return db
    }

}