package dev.putra.domain.utangku.domain.usecase

import dev.putra.domain.utangku.domain.model.Utang
import dev.putra.domain.utangku.domain.repo.UtangkuRepository
import dev.putra.libraries.network.usecase.UseCase

/**
 * Created by Chyrus on 3/28/2020.
 */
class GetListUtangUseCase(private val utangkuRepository: UtangkuRepository) :
    UseCase<List<Utang>>() {

    override suspend fun build() = utangkuRepository.fetchListUtang()
}