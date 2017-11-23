package server.services

import com.movile.kotlin.commons.serialization.toMap
import org.jetbrains.exposed.sql.transactions.transaction
import server.domain.models.User
import server.domain.models.UserEntity
import server.domain.models.Users

class UserService {

    suspend fun create(user: User) {

        transaction {
            if(!UserEntity.find { Users.username eq user.username }.empty()) {
                throw Exception("User '${user.username}' already exists")
            }

            UserEntity.new {
                username = user.username
                email = user.email
            }
        }
    }

    suspend fun find(id: Int): UserEntity? = transaction { return@transaction UserEntity.findById(id) }

}