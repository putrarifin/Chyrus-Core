package dev.putra.domain.directus.data.source.remote.response

import dev.putra.domain.directus.data.model.User
import dev.putra.domain.directus.domain.model.UserModel

/**
 * Created by Chyrus on 3/29/2020.
 */
internal data class UserResponse(
    val data: User?
) {
    fun toDomainModel() = UserModel(
        id = data?.id ?: -1,
        status = data?.status.orEmpty(),
        role = data?.role ?: -1,
        fullName = "${data?.first_name} ${data?.last_name}",
        firstName = data?.first_name.orEmpty(),
        lastName = data?.last_name.orEmpty(),
        email = data?.email.orEmpty(),
        photoId = data?.avatar ?: -1
    )
}