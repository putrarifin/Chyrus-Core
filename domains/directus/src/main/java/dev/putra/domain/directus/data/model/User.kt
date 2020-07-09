package dev.putra.domain.directus.data.model

/**
 * Created by Chyrus on 3/29/2020.
 */
internal data class User(
    val id: Int?,
    val status: String?,
    val role: Int?,
    val first_name: String?,
    val last_name: String?,
    val email: String?,
    val avatar: Int?
)
internal data class UserAuth(
    val id: String?,
    val status: String?,
    val role: String?,
    val first_name: String?,
    val last_name: String?,
    val email: String?,
    val avatar: String?
)