package dev.putra.domain.directus.domain.usecase

import dev.putra.domain.directus.domain.model.UserModel
import dev.putra.domain.directus.domain.repo.DirectusRepository
import dev.putra.libraries.network.usecase.UseCaseWithParam

/**
 * Created by Chyrus on 3/29/2020.
 */
class UpdateUserUseCase(private val directusRepository: DirectusRepository) :
    UseCaseWithParam<UserModel, UpdateUserUseCase.Params>() {

    override suspend fun build(params: Params) =
        directusRepository.updateUser(
            id = params.id,
            first_name = params.first_name,
            last_name = params.last_name,
            email = params.email,
            password = params.password
        )

    data class Params(
        val id: Int,
        val first_name: String? = null,
        val last_name: String? = null,
        val email: String? = null,
        val password: String? = null
    )
}