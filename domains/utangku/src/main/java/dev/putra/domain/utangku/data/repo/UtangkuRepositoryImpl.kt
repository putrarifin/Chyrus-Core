package dev.putra.domain.utangku.data.repo

import dev.putra.domain.utangku.data.source.remote.UtangkuService
import dev.putra.domain.utangku.domain.repo.UtangkuRepository

/**
 * Created by Chyrus on 3/28/2020.
 */
internal class UtangkuRepositoryImpl(
    private val utangkuService: UtangkuService
) :
    UtangkuRepository {

    override suspend fun fetchListUtang() = utangkuService.getListUtang().list.map {
        it.toDomainModel()
    }
}