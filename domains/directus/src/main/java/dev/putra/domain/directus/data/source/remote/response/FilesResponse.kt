package dev.putra.domain.directus.data.source.remote.response

import dev.putra.domain.directus.data.model.Files
import dev.putra.domain.directus.domain.model.FileModel

/**
 * Created by Chyrus on 3/29/2020.
 */
internal data class FilesResponse(
    val data: Files?
) {
    fun toDomainModel() = FileModel(
        id = data?.id ?: 0,
        url = data?.data?.full_url.orEmpty(),
        fileName = data?.filename_download.orEmpty(),
        type = data?.type.orEmpty()
    )
}