package com.canlioya.technicaltest.data.network

import com.canlioya.technicaltest.model.Photo
import com.squareup.moshi.Json

/**
 * Photo data-transfer-object
 * used for mapping backend JSON schema
 *
 * @property albumId
 * @property photoId
 * @property photoTitle
 * @property url
 * @property thumbnailUrl
 * @constructor Create empty Photo d t o
 */
data class PhotoDTO(
    val albumId : Int,
    @Json(name = "id") val photoId : Int,
    @Json(name = "title") val photoTitle : String,
    val url : String,
    val thumbnailUrl : String
)

/**
 * Extension function for mapping PhotoDTO
 * to [Photo] domain model
 *
 */
fun PhotoDTO.toDomainModel() = Photo(this.photoTitle, this.url,this.thumbnailUrl)

/**
 * Extension function for mapping a list of PhotoDTOs
 * to a list of Photo domain models
 *
 * @return
 */
fun List<PhotoDTO>.toDomainModelList() : List<Photo> {
    return this.map {
        it.toDomainModel()
    }
}