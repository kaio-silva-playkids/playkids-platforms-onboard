package server.security

import io.ktor.application.ApplicationCall
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.request.header
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.AttributeKey
import server.services.AuthenticationService

val SECURITY_CONTEXT_ATTRIBUTE = AttributeKey<SecurityContext>("security_context")

fun Route.authenticate(authenticationService: AuthenticationService) {

    intercept(ApplicationCallPipeline.Infrastructure) {
        call.request.header(HttpHeaders.Authorization)
                ?.let { extractSecurityContext(authenticationService, it) }
                ?.also { call.attributes.put(SECURITY_CONTEXT_ATTRIBUTE, it) }
                ?: call.respond(HttpStatusCode.Forbidden, "Invalid credentials")
    }

}

fun ApplicationCall.securityContext(): SecurityContext =
        attributes[SECURITY_CONTEXT_ATTRIBUTE]

private suspend fun extractSecurityContext(authenticationService: AuthenticationService,
                                           authorizationHeader: String): SecurityContext? =
        authorizationHeader
                .takeIf { it.startsWith("Bearer ") }
                ?.let { it.substring(7) }
                ?.let { authenticationService.generateUserSecurityContext(it) }