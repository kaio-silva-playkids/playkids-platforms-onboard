package playkids.onboard.server.persistence

interface Configuration {

    val user: String
    val password: String
    val driver: String
    fun url():String

}