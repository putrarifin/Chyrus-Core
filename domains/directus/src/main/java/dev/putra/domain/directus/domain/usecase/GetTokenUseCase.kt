package dev.putra.domain.directus.domain.usecase

import dev.putra.domain.directus.domain.model.TokenCache
import dev.putra.domain.directus.domain.repo.DirectusRepository
import dev.putra.libraries.core.interactor.UseCase

/**
 * Created by Chyrus on 3/29/2020.
 */
class GetTokenUseCase(private val directusRepository: DirectusRepository) : UseCase<TokenCache> {
    override fun invoke(): TokenCache = directusRepository.getToken()
}