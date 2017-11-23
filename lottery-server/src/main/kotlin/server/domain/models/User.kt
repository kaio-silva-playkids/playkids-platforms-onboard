package server.domain.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Users: IntIdTable() {
    val username = varchar("username", length = 50)
    val email = varchar("email", length = 50)
}

data class User(var username: String, var email: String) {
    init {
        require(username.isNotEmpty())
        require(email.isNotEmpty())
    }
}

class UserEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<UserEntity>(Users)

    var username by Users.username
    var email by Users.email

    fun asUser(): User = User(username, email)

}