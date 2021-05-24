package com.canlioya.technicaltest.model

/**
 * Photo for domain model
 *
 * @property photoTitle
 * @property url
 * @property thumbnailUrl
 * @constructor Create empty Photo
 */
data class Photo(
    val photoTitle : String,
    val url : String,
    val thumbnailUrl : String
)
