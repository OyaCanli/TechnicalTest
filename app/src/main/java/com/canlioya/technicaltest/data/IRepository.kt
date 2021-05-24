package com.canlioya.technicaltest.data

import com.canlioya.technicaltest.model.Album
import com.canlioya.technicaltest.model.Result
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the app's repository.
 * UI components will claim data from the repository
 *
 * @constructor Create empty I repository
 */
interface IRepository {

    suspend fun getAllAlbums(): Flow<Result<List<Album>?>>

}