package dev.putra.domain.directus.domain.repo

import dev.putra.domain.directus.domain.model.FileModel
import dev.putra.domain.directus.domain.model.TokenCache
import dev.putra.domain.directus.domain.model.UserModel
import java.io.File

/**
 * Created by Chyrus on 3/29/2020.
 */
interface DirectusRepository {
    suspend fun getFile(id: Int): FileModel
    suspend fun uploadFile(file: File): FileModel
    suspend fun login(username: String, password: String): UserModel
    suspend fun fetchToken(token: String): TokenCache
    suspend fun createUser(
        email: String,
        password: String,
        role: Int?,
        status: String? = "active"
    ): UserModel

    suspend fun updateUser(
        id: Int,
        first_name: String?,
        last_name: String?,
        email: String?,
        password: String?
    ): UserModel

    suspend fun userMe(): UserModel
    fun getToken(): TokenCache
}