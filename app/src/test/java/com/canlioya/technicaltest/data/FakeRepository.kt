package com.canlioya.technicaltest.data

import com.canlioya.technicaltest.model.Result
import kotlinx.coroutines.flow.flow
import java.io.IOException

class SuccessFakeRepository : IRepository {

    override suspend fun getAllAlbums() = flow {
        emit(Result.Loading)
        emit(Result.Success(mappedAlbumList))
    }

    override suspend fun getPhotosForAlbum(albumId: Int) = flow {
        emit(Result.Loading)
        emit(Result.Success(mappedPhotoList))
    }
}

class FailFakeRepository : IRepository {

    override suspend fun getAllAlbums() = flow {
        emit(Result.Loading)
        emit(Result.Error(IOException()))
    }

    override suspend fun getPhotosForAlbum(albumId: Int) = flow {
        emit(Result.Loading)
        emit(Result.Error(IOException()))
    }
}