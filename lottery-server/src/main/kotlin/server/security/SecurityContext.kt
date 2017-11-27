package server.security

import server.domain.models.User

sealed class SecurityContext(val principal: String)

object RootSecurityContext : SecurityContext("root")
data class UserSecurityContext(val user: User) : SecurityContext(user.username)