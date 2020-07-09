package dev.putra.domain.utangku.data.source.remote.response

import dev.putra.domain.utangku.data.model.UtangModel

/**
 * Created by Chyrus on 3/28/2020.
 */
internal data class ListUtangResponse(
    val list: List<UtangModel> = emptyList()
)