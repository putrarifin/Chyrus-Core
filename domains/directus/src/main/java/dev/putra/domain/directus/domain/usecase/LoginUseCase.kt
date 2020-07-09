package dev.putra.domain.directus.domain.usecase

import dev.putra.domain.directus.domain.model.UserModel
import dev.putra.domain.directus.domain.repo.DirectusRepository
import dev.putra.libraries.network.usecase.UseCaseWithParam

/**
 * Created by Chyrus on 3/29/2020.
 */
class LoginUseCase(private val directusRepository: DirectusRepository) :
    UseCaseWithParam<UserModel, LoginUseCase.Params>() {

    override suspend fun build(params: Params) =
        directusRepository.login(username = params.username, password = params.password)

    data class Params(val username: String, val password: String)
}