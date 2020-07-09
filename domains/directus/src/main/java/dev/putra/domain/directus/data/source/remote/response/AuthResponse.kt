package dev.putra.domain.directus.data.source.remote.response

import dev.putra.domain.directus.data.model.UserAuth
import dev.putra.domain.directus.domain.model.TokenCache
import dev.putra.domain.directus.domain.model.UserModel

/**
 * Created by Chyrus on 3/29/2020.
 */
internal data class LoginResponse(
    val data: AuthResponse
)

internal data class AuthResponse(
    val token: String?,
    val user: UserAuth?
) {
    fun toDomainModel() = UserModel(
        id = user?.id?.toInt() ?: -1,
        status = user?.status.orEmpty(),
        role = user?.role?.toInt() ?: -1,
        fullName = "${user?.first_name} ${user?.last_name}",
        firstName = user?.first_name.orEmpty(),
        lastName = user?.last_name.orEmpty(),
        email = user?.email.orEmpty(),
        photoId = user?.avatar?.toInt() ?: -1
    )

    fun toTokenDomain() = TokenCache(
        token = token.orEmpty()
    )
}