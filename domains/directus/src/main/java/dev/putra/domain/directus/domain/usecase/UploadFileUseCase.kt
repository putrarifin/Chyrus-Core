package dev.putra.domain.directus.domain.usecase

import dev.putra.domain.directus.domain.model.FileModel
import dev.putra.domain.directus.domain.repo.DirectusRepository
import dev.putra.libraries.network.usecase.UseCaseWithParam
import java.io.File

/**
 * Created by Chyrus on 3/31/2020.
 */
class UploadFileUseCase(private val directusRepository: DirectusRepository) :
    UseCaseWithParam<FileModel, UploadFileUseCase.Params>() {

    override suspend fun build(params: Params): FileModel =
        directusRepository.uploadFile(params.file)

    data class Params(val file: File)
}