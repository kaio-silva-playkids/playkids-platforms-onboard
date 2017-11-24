package server.services

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import server.domain.models.User
import server.domain.models.UserEntity
import server.domain.models.Users
import server.security.Hash

class UserService {

    fun create(user: User) {

        transaction {
            if(!UserEntity.find { Users.username eq user.username }.empty()) {
                // TODO Raise 409 (Conflict)
                throw Exception("User '${user.username}' already exists")
            }

            UserEntity.new {
                username = user.username
                email = user.email
                password = user.password.orEmpty()
                credit =  50

            }
        }
    }

    fun find(id: Int): UserEntity? = transaction { return@transaction UserEntity.findById(id) }

    fun find(username: String): UserEntity? = transaction { return@transaction UserEntity.find { Users.username eq username }.first() }

    fun authenticate(username: String, password: String): UserEntity? {

        transaction {
            return@transaction UserEntity.find { Users.username.eq(username) and Users.password.eq(Hash.sha512(password)) }.first()
        }

        return null
    }

}