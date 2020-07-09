package dev.putra.domain.utangku.data.model

import dev.putra.domain.utangku.domain.model.Utang

/**
 * Created by Chyrus on 3/28/2020.
 */
internal data class UtangModel(
    val debtName: String?,
    val debtAmount: String?
) {
    fun toDomainModel() = Utang(
        debtName = debtName.orEmpty(),
        debtAmount = debtAmount.orEmpty()
    )
}