package dev.putra.domain.directus.domain.usecase

import dev.putra.domain.directus.domain.model.UserModel
import dev.putra.domain.directus.domain.repo.DirectusRepository
import dev.putra.libraries.network.usecase.UseCaseWithParam

/**
 * Created by Chyrus on 3/29/2020.
 */
class CreateUserUseCase(private val directusRepository: DirectusRepository) :
    UseCaseWithParam<UserModel, CreateUserUseCase.Params>() {

    override suspend fun build(params: Params) =
        directusRepository.createUser(
            email = params.email,
            password = params.password,
            role = params.role,
            status = params.status
        )

    data class Params(
        val email: String,
        val password: String,
        val role: Int?,
        val status: String?
    )
}