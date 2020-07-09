package dev.putra.domain.directus.domain.model

/**
 * Created by Chyrus on 3/29/2020.
 */
data class UserModel(
    val id: Int,
    val status: String,
    val role: Int,
    val fullName: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val photoId: Int
)