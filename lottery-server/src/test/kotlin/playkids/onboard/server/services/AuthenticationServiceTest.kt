package playkids.onboard.server.services

import com.nhaarman.mockito_kotlin.*
import com.winterbe.expekt.should
import commons.test.dsl.case
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import playkids.onboard.server.models.User
import playkids.onboard.server.models.UserEntity
import playkids.onboard.server.models.Users
import playkids.onboard.server.security.Hash
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class AuthenticationServiceTest: Spek ({

    val userService = mock<UserService>()
    val clock = Clock.fixed(Instant.EPOCH, ZoneId.of("UTC"))

    val authenticationService = AuthenticationService(userService, clock, "test")

    val user = User(username = "username", email = "user@email.com", password = Hash.sha512("test").toLowerCase())

    beforeEachTest {
        reset(userService)
    }

    describe("Authentication Service Test") {

        context("Authenticate") {

            case("should authenticate a new user by username") {

                val userEntity: UserEntity = UserEntity(EntityID(1, Users))
                with(userEntity) {
                    this.username = user.username
                    this.password = user.password
                }

                whenever(userService.authenticate(eq(user.username), eq(user.password))).thenReturn(userEntity)

                authenticationService.authenticate(user.username, user.password).should.be.equal(user)

            }

            case("should authenticate a new user by email") {

                val userEntity: UserEntity = UserEntity(EntityID(1, Users))
                with(userEntity) {
                    this.email = user.email
                    this.password = user.password
                }

                whenever(userService.authenticate(eq(user.email), eq(user.password))).thenReturn(userEntity)

                authenticationService.authenticate(user.email, user.password).should.be.equal(user)

            }

            case("should authenticate fail") {

                whenever(userService.authenticate(any(), any())).thenReturn(null)

                authenticationService.authenticate(user.email, user.password).should.be.`null`

            }

        }

    }

})