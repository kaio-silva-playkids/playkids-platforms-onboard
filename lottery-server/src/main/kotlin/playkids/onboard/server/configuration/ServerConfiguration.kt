package playkids.onboard.server.configuration

import com.typesafe.config.ConfigFactory

object ServerConfiguration {

    val config = ConfigFactory.load().getConfig("server")!!

}