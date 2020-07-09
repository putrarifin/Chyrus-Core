package dev.putra.domain.directus.data.source.remote

import dev.putra.domain.directus.data.source.remote.request.AuthRequest
import dev.putra.domain.directus.data.source.remote.request.CreateUserRequest
import dev.putra.domain.directus.data.source.remote.request.RefreshTokenRequest
import dev.putra.domain.directus.data.source.remote.request.UpdateUserRequest
import dev.putra.domain.directus.data.source.remote.response.FilesResponse
import dev.putra.domain.directus.data.source.remote.response.LoginResponse
import dev.putra.domain.directus.data.source.remote.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * Created by Chyrus on 3/29/2020.
 */
internal interface DirectusService {

    @POST("auth/authenticate")
    suspend fun login(@Body body: AuthRequest): LoginResponse

    @POST("auth/refresh")
    suspend fun refreshToken(@Body body: RefreshTokenRequest): LoginResponse

    @POST("users")
    suspend fun createUser(@Body body: CreateUserRequest): UserResponse

    @PATCH("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body body: UpdateUserRequest): UserResponse

    @GET("users/me")
    suspend fun userMe(): UserResponse

    @GET("files/{id}")
    suspend fun getFile(@Path("id") id: Int): FilesResponse

    @Multipart
    @POST("files")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    ): FilesResponse

}