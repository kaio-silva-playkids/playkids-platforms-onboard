package playkids.onboard.server.configuration

import com.typesafe.config.ConfigFactory
import playkids.onboard.server.persistence.Configuration

class PostgreSQLConfiguration : Configuration {

    private val config = ConfigFactory.load().getConfig("postgresql")
    val host = config.getString("host")
    val port = config.getInt("port")
    val database = config.getString("database")
    override val user = config.getString("user")
    override val password = config.getString("password")
    override val driver = "org.postgresql.Driver"

    override fun url(): String = "jdbc:postgresql://$host:$port/$database"
}
