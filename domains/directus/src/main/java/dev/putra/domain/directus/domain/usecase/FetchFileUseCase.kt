package dev.putra.domain.directus.domain.usecase

import dev.putra.domain.directus.domain.model.FileModel
import dev.putra.domain.directus.domain.repo.DirectusRepository
import dev.putra.libraries.network.usecase.UseCaseWithParam

/**
 * Created by Chyrus on 3/31/2020.
 */
class FetchFileUseCase(private val directusRepository: DirectusRepository) :
    UseCaseWithParam<FileModel, FetchFileUseCase.Params>() {

    override suspend fun build(params: Params): FileModel =
        directusRepository.getFile(params.idFile)

    data class Params(
        val idFile: Int
    )
}