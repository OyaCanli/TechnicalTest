package com.canlioya.technicaltest.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object pointing to the desired URL
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * Interface used by Retrofit
 */
interface AlbumApiService {
    /**
     * Returns a Retrofit callback that delivers the list of all items in the "albums" endpoint
     */
    @GET("albums")
    suspend fun getAlbums(): List<AlbumDTO>?

    /**
     * Returns a Retrofit callback that delivers the list of all items in the "users" endpoint
     */
    @GET("users")
    suspend fun getUserWithId(@Query("id") userId : Int) : List<UserDTO>?

    /**
     * Returns a Retrofit callback that delivers the list of photos with the specified albumId
     * @param albumId
     */
    @GET("photos")
    suspend fun getPhotosForAlbum(@Query("albumId") albumId: Int): List<PhotoDTO>?
}

/**
 * Interface to provide an access to retrofitService instance
 * Extracted as an interface to be doubled in tests
 */
interface IApiProvider {
    val retrofitService : AlbumApiService
}

/**
 * Singleton utility class to provide an access to retrofitService instance
 *
 */
object ApiProvider : IApiProvider{
    /**
     * Returns an instance of Retrofit api service, created lazily and cached
     */
    override val retrofitService : AlbumApiService by lazy {
        retrofit.create(AlbumApiService::class.java)
    }
}