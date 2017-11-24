package server.security

import java.security.MessageDigest

object Hash {

    fun sha512(input: String) = hashString("SHA-512", input)

    private fun hashString(type: String, input: String): String =
        MessageDigest.getInstance(type)
                .digest(input.toByteArray())
                .map { String.format("%02X", it) }
                .joinToString(separator = "")

}