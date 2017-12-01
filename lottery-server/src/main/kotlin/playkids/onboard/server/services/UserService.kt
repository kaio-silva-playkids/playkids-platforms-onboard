package playkids.onboard.server.services

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.transaction
import playkids.onboard.server.models.User
import playkids.onboard.server.models.UserEntity
import playkids.onboard.server.models.Users
import playkids.onboard.server.security.Hash

class UserService {

    fun create(user: User): User? {

        var inserted: User? = null

        transaction {
            if (!UserEntity.find { Users.username eq user.username }.empty()) {
                // TODO Raise 409 (Conflict)
                throw Exception("User '${user.username}' already exists")
            }

            inserted = UserEntity.new {
                username = user.username
                email = user.email
                password = Hash.sha512(user.password.orEmpty()).toLowerCase()
                credit = 50
            }.asUser()
        }

        return inserted
    }

    fun find(id: Int): UserEntity? = transaction { return@transaction UserEntity.findById(id) }

    fun find(username: String): UserEntity? =
            transaction { return@transaction UserEntity.find { Users.username eq username }.firstOrNull() }

    fun authenticate(login: String, password: String): UserEntity? =
        transaction { return@transaction UserEntity.find {
            (Users.username.eq(login) or Users.email.eq(login)) and Users.password.eq(password)
        }.firstOrNull() }

}