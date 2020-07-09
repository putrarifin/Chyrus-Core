package dev.putra.domain.utangku.data.source.remote

import dev.putra.domain.utangku.data.source.remote.response.ListUtangResponse
import retrofit2.http.GET

/**
 * Created by Chyrus on 3/28/2020.
 */
internal interface UtangkuService {

    @GET("items/utang")
    suspend fun getListUtang() : ListUtangResponse

}