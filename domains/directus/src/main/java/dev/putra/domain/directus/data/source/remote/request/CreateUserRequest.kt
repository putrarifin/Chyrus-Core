package dev.putra.domain.directus.data.source.remote.request

/**
 * Created by Chyrus on 3/29/2020.
 */
internal data class CreateUserRequest(
    val email: String,
    val password: String,
    val role: Int?,
    val status: String? = "active"
)