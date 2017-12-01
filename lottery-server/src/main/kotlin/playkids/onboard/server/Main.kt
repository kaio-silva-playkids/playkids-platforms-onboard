package playkids.onboard.server

class Main {

    companion object {

        @JvmStatic fun main(args: Array<String>) {

            val server = Server(8082)

            server.start()

        }
    }
}