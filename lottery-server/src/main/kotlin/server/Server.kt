package server

import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.*
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import server.routes.api
import server.configuration.ServicesConfiguration.authenticationService
import server.configuration.ServicesConfiguration.userService
import server.configuration.ServicesConfiguration.lotteryService
import server.configuration.ServicesConfiguration.ticketService
import server.configuration.PostgreSQLConfiguration
import server.persistence.Database
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.datatype.jsr310.*
import org.slf4j.LoggerFactory

class Server(val port: Int) {

    private val database: Database = object :Database(PostgreSQLConfiguration()){}

    fun start() {

        // TODO refact
        database.start()

        embeddedServer(Netty, port) {

            log.info("Installation pipeline...")
            install(DefaultHeaders.Feature)
            install(CallLogging.Feature)
            install(ConditionalHeaders.Feature)
            install(Compression.Feature) {
                default()
            }
            install(CORS.Feature) {
                anyHost()
                method(HttpMethod.Put)
                method(HttpMethod.Delete)
                header(HttpHeaders.Authorization)
            }

            install(ContentNegotiation) {
                jackson {
                    configure(SerializationFeature.INDENT_OUTPUT, true)
                    registerModule(JavaTimeModule())
                    registerModule(JodaModule())
                }
            }

            log.info("Routing pipeline...")
            routing {
                api(authenticationService, userService, lotteryService, ticketService)
            }
        }.start(wait = true)

    }

    companion object {
        val logger = LoggerFactory.getLogger(Server::class.qualifiedName)
    }
}