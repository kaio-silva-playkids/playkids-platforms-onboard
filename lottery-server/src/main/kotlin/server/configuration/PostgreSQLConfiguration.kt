package server.configuration

import com.typesafe.config.ConfigFactory
import org.jetbrains.exposed.sql.Database

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SchemaUtils.drop
import server.domain.models.Lotteries
import server.domain.models.Tickets
import server.domain.models.Users

object PostgreSQLConfiguration {

    //TODO get configuration from conf.

    private val config = ConfigFactory.load().getConfig("postgresql")
    private val host = config.getString("host")
    private val port = config.getInt("port")
    private val database = config.getString("database")
    private val user = config.getString("user")
    private val password = config.getString("password")

    val db = Database.connect("jdbc:postgresql://$host:$port/$database",
            "org.postgresql.Driver", user, password)

    init {

        transaction {
            drop(Users, Tickets, Lotteries)
            create(Users, Tickets, Lotteries)
        }
    }

}