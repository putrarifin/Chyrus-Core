package dev.putra.domain.directus.domain.usecase

import dev.putra.domain.directus.domain.model.UserModel
import dev.putra.domain.directus.domain.repo.DirectusRepository
import dev.putra.libraries.network.usecase.UseCase
import dev.putra.libraries.network.usecase.UseCaseWithParam

/**
 * Created by Chyrus on 3/29/2020.
 */
class FetchMyUserUseCase(private val directusRepository: DirectusRepository) :
    UseCase<UserModel>() {

    override suspend fun build() =
        directusRepository.userMe()
}