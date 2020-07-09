package dev.putra.domain.utangku.domain.repo

import dev.putra.domain.utangku.domain.model.Utang

/**
 * Created by Chyrus on 3/28/2020.
 */
interface UtangkuRepository {

    suspend fun fetchListUtang(): List<Utang>

}