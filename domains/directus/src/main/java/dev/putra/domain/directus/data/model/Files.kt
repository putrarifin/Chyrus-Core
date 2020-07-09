package dev.putra.domain.directus.data.model

/**
 * Created by Chyrus on 3/29/2020.
 */
internal data class Files(
    val id: Int?,
    val storage: String?,
    val private_hash: String?,
    val filename_disk: String?,
    val filename_download: String?,
    val title: String?,
    val type: String?,
    val uploaded_by: Int?,
    val uploaded_on: String?,
    val charset: String?,
    val filesize: Long?,
    val width: Int?,
    val height: Int?,
    val duration: Int?,
    val description: String?,
    val location: String?,
    val checksum: String?,
    val data: Data?
) {

    data class Data(
        val full_url: String?,
        val url: String?,
        val thumbnails: List<Thumbnails>? = emptyList()
    ) {
        data class Thumbnails(
            val url: String?,
            val relative_url: String?,
            val dimension: String?,
            val width: Int?,
            val height: Int?
        )
    }

}