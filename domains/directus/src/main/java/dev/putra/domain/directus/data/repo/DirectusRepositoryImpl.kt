package dev.putra.domain.directus.data.repo

import dev.putra.domain.directus.data.source.remote.DirectusService
import dev.putra.domain.directus.data.source.remote.request.AuthRequest
import dev.putra.domain.directus.data.source.remote.request.CreateUserRequest
import dev.putra.domain.directus.data.source.remote.request.RefreshTokenRequest
import dev.putra.domain.directus.data.source.remote.request.UpdateUserRequest
import dev.putra.domain.directus.domain.model.FileModel
import dev.putra.domain.directus.domain.model.TokenCache
import dev.putra.domain.directus.domain.model.UserModel
import dev.putra.domain.directus.domain.repo.DirectusRepository
import dev.putra.libraries.cache.preference.secure.SecuredPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * Created by Chyrus on 3/29/2020.
 */
internal class DirectusRepositoryImpl(
    private val securedPreference: SecuredPreference,
    private val directusService: DirectusService
) : DirectusRepository {

    override suspend fun getFile(id: Int): FileModel = directusService.getFile(id).toDomainModel()

    override suspend fun uploadFile(file: File): FileModel = directusService.uploadFile(
        MultipartBody.Part.createFormData(
            "data",
            file.name,
            file.asRequestBody("image/*".toMediaTypeOrNull())
        )
    ).toDomainModel()

    override suspend fun login(username: String, password: String): UserModel =
        directusService.login(
            AuthRequest(username, password)
        ).also {
            //save token to cache
            withContext(Dispatchers.IO) {
                securedPreference.set(TokenCache.KEY, it.data.token.orEmpty())
            }
        }.data.toDomainModel()

    override suspend fun fetchToken(token: String): TokenCache = directusService.refreshToken(
        RefreshTokenRequest(token)
    ).also {
        //save token to cache
        withContext(Dispatchers.IO) {
            securedPreference.set(TokenCache.KEY, it.data.token.orEmpty())
        }
    }.data.toTokenDomain()

    override fun getToken(): TokenCache = TokenCache(securedPreference.getString(TokenCache.KEY))

    override suspend fun createUser(
        email: String,
        password: String,
        role: Int?,
        status: String?
    ): UserModel = directusService.createUser(
        CreateUserRequest(
            email,
            password,
            role,
            status
        )
    ).toDomainModel()

    override suspend fun updateUser(
        id: Int,
        first_name: String?,
        last_name: String?,
        email: String?,
        password: String?
    ): UserModel = directusService.updateUser(
        id = id,
        body = UpdateUserRequest(
            first_name = first_name,
            last_name = last_name,
            email = email,
            password = password
        )
    ).toDomainModel()

    override suspend fun userMe(): UserModel = directusService.userMe().toDomainModel()
}