package com.canlioya.technicaltest.data

import com.canlioya.technicaltest.data.network.AlbumApiService
import com.canlioya.technicaltest.data.network.AlbumDTO
import com.canlioya.technicaltest.data.network.PhotoDTO
import com.canlioya.technicaltest.data.network.UserDTO
import java.io.IOException

//////////      Test doubles     ////////////////////////
class SuccessfullRetrofitService : AlbumApiService {
    override suspend fun getAlbums(): List<AlbumDTO>? {
        return sampleAlbumDTOList
    }

    override suspend fun getPhotosForAlbum(albumId: Int): List<PhotoDTO>? {
        return samplePhotoDTOListForFirstAlbum
    }

    override suspend fun getUserWithId(userId: Int): List<UserDTO>? {
        return when(userId){
            1 -> listOf(sampleUser1)
            2 -> listOf(sampleUser2)
            3 -> listOf(sampleUser3)
            else -> emptyList()
        }
    }
}

class FailingRetrofitService : AlbumApiService {
    override suspend fun getAlbums(): List<AlbumDTO>? {
        throw IOException()
    }

    override suspend fun getPhotosForAlbum(albumId: Int): List<PhotoDTO>? {
        throw IOException()
    }

    override suspend fun getUserWithId(userId: Int): List<UserDTO>? {
        throw IOException()
    }
}