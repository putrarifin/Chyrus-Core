package dev.putra.domain.directus.data.source.remote.request

/**
 * Created by Chyrus on 3/29/2020.
 */
internal data class UpdateUserRequest(
    val first_name: String? = null,
    val last_name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val role: Int? = null,
    val status: String? = null
)