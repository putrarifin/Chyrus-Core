package dev.putra.domain.directus.domain.usecase

import dev.putra.domain.directus.domain.model.TokenCache
import dev.putra.domain.directus.domain.repo.DirectusRepository
import dev.putra.libraries.network.usecase.UseCaseWithParam

/**
 * Created by Chyrus on 3/29/2020.
 */
class RefreshTokenUseCase(private val directusRepository: DirectusRepository) :
    UseCaseWithParam<TokenCache, RefreshTokenUseCase.Params>() {

    override suspend fun build(params: Params) =
        directusRepository.fetchToken(params.token)

    data class Params(val token: String)
}