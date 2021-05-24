package com.canlioya.technicaltest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Album domain model
 *
 * @property albumId
 * @property albumTitle
 * @property userFullName
 * @constructor Create empty Album
 */
@Parcelize
data class Album (
    val albumId : Int,
    val albumTitle : String,
    val userFullName : String?
) : Parcelable