package com.canlioya.technicaltest.data


import com.canlioya.technicaltest.data.network.AlbumDTO
import com.canlioya.technicaltest.data.network.IApiProvider
import com.canlioya.technicaltest.data.network.UserDTO
import com.canlioya.technicaltest.model.Album
import com.canlioya.technicaltest.model.Result
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository implementation for getting and providing data to the UI
 *
 * @property albumApiProvider provides access to retrofit service
 * @constructor is not meant to be manually created. Should be provided
 * as singleton by dependency injection
 */
@Singleton
class Repository @Inject constructor(private val albumApiProvider : IApiProvider) : IRepository {

    /**
     * <p>Attempts to fetch albums from the backend
     * and wraps the state in a flow of [Result].
     * Emits Result.Loading state in the beginning.
     * If an exception occurs during fetching, emits
     * Result.Error. If data is successfully fetched,
     * it wraps the data inside Result.Success </p>
     *
     */
    override suspend fun getAllAlbums() = flow {
        emit((Result.Loading))
        try {
            val albumList = albumApiProvider.retrofitService.getAlbums()

            if(albumList?.isNotEmpty() == true){
                val mappedList = fetchUsersAndMapToAlbums(albumList)
                emit(Result.Success(mappedList))
            } else {
                emit(Result.Success(emptyList<Album>()))
            }
        } catch (e: HttpException) {
            Timber.e(e)
            emit(Result.Error(e))
        } catch (e: IOException) {
            Timber.e(e)
            emit(Result.Error(e))
        }
    }

    /**
     * <p>For each album, we get the userId but not the name of the user
     * User names should be fetched separately for each userId.
     * As there are multiple albums having same user, for optimization,
     * an HashMap is used for saving fetched user names and not
     * fetching the user multiple times.</p>
     *
     * Once users are fetched, albums and user names are mapped to
     * Album domain models
     *
     * @param albumList
     * @return
     */
    private suspend fun fetchUsersAndMapToAlbums(albumList: List<AlbumDTO>): List<Album> {
        val userMap = HashMap<Int, String>()
        albumList.forEach { album ->
            if (userMap[album.userId] == null) {
                Timber.d("fetching user for userID : ${album.userId}")
                val user: UserDTO? =
                    albumApiProvider.retrofitService.getUserWithId(album.userId)?.get(0)
                user?.fullName?.let {
                    userMap[album.userId] = it
                }
            }
        }

        return albumList.map {
            val userName = userMap[it.userId]
            Album(it.albumId, it.albumTitle, userName)
        }
    }
}