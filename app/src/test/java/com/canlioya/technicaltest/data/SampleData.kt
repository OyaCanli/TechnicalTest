package com.canlioya.technicaltest.data

import com.canlioya.technicaltest.data.network.AlbumDTO
import com.canlioya.technicaltest.data.network.PhotoDTO
import com.canlioya.technicaltest.data.network.UserDTO
import com.canlioya.technicaltest.data.network.toDomainModelList
import com.canlioya.technicaltest.model.Album

val sampleAlbumDTOList = listOf(
    AlbumDTO(1,1,"First album"),
    AlbumDTO(2,2,"Second album"),
    AlbumDTO(3,3,"Third album")
)

val sampleAlbum1 = Album(albumId=1, albumTitle="First album", userFullName="John Doe")
val sampleAlbum2 = Album(albumId=2, albumTitle="Second album", userFullName="Merry Smith")
val sampleAlbum3 = Album(albumId=3, albumTitle="Third album", userFullName="Alexandre Black")

val mappedAlbumList = listOf(sampleAlbum1, sampleAlbum2, sampleAlbum3)

val sampleUser1 = UserDTO(1, "John Doe", "johnny", null, null, null, null, null)
val sampleUser2 = UserDTO(2, "Merry Smith", "merrylin", null, null, null, null, null)
val sampleUser3 = UserDTO(3, "Alexandre Black", "Alex", null, null, null, null, null)

val samplePhotoDTOListForFirstAlbum = listOf(
    PhotoDTO(1, 1, "first photo title", "https://via.placeholder.com/600/771796", "https://via.placeholder.com/600/771796"),
    PhotoDTO(1, 2, "first photo title", "https://via.placeholder.com/600/771796", "https://via.placeholder.com/600/771796")
)

val mappedPhotoList = samplePhotoDTOListForFirstAlbum.toDomainModelList()