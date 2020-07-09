package dev.putra.domain.directus.domain.model

/**
 * Created by Chyrus on 3/29/2020.
 */
data class FileModel(
    val id: Int,
    val url: String,
    val fileName: String,
    val type: String
)