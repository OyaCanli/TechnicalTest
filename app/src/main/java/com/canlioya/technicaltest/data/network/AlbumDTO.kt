package com.canlioya.technicaltest.data.network

import com.squareup.moshi.Json

/**
 * Album data-transfer-object
 * used for mapping backend JSON schema
 *
 * @property userId
 * @property albumId
 * @property albumTitle
 * @constructor Create empty Album d t o
 */
data class AlbumDTO(
    val userId : Int,
    @Json(name = "id") val albumId : Int,
    @Json(name = "title") val albumTitle : String
)