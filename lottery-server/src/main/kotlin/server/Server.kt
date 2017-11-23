package server

import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.*
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import org.jetbrains.exposed.sql.name
import server.routes.api
import server.configuration.UserConfiguration.userService
import server.configuration.PostgreSQLConfiguration.db
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.*
import io.ktor.jackson.jackson
import org.slf4j.LoggerFactory

class Server(val port: Int) {

    fun start() {

        //TODO must avoid lazy object initialization
        require(db.name.isNotBlank())

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
                }
            }

            log.info("Routing pipeline...")
            routing {
                api(userService)
            }
        }.start(wait = true)

    }

    companion object {
        val logger = LoggerFactory.getLogger(Server::class.qualifiedName)
    }
}