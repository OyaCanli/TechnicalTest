package com.canlioya.technicaltest.data

import com.canlioya.technicaltest.model.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


@ExperimentalCoroutinesApi
class RepositoryTest {

    /**
     * Helper method for initializing the repository with
     * a fake api service implementation that pretends to
     * returns successfully
     */
    private fun setAlbumsSuccessfullyFetched() = Repository(SuccessfullRetrofitService())

    /**
     * Helper method for initializing the repository with
     * a fake api service implementation that @throws IOException
     */
    private fun setErrorWhileFetchingAlbums() = Repository(FailingRetrofitService())


    @Test
    fun getAllAlbums_whenSuccessfull_firstReturnsLoading() = runBlockingTest {
        val repository = setAlbumsSuccessfullyFetched()
        val firstEmission = repository.getAllAlbums().first()
        assert(firstEmission is Result.Loading)
    }

    @Test
    fun getAllAlbums_whenNotSuccessfull_firstReturnsLoading() = runBlockingTest{
        val repository = setErrorWhileFetchingAlbums()
        val firstEmission = repository.getAllAlbums().first()
        assert(firstEmission is Result.Loading)
    }

    @Test
    fun getAllAlbums_whenSuccessfull_returnSuccessWithSampleList() = runBlockingTest {
        val repository = setAlbumsSuccessfullyFetched()
        val secondEmission = repository.getAllAlbums().drop(1).first()
        assertThat(secondEmission, `is`(Result.Success(mappedAlbumList)))
    }

    @Test
    fun getAllAlbums_whenNotSuccessfull_returnError() = runBlockingTest {
        val repository = setErrorWhileFetchingAlbums()
        val secondEmission = repository.getAllAlbums().drop(1).first()
        assert(secondEmission is Result.Error)
    }

    @Test
    fun getPhotosForAlbum_whenSuccessfull_firstReturnsLoading() = runBlockingTest {
        val repository = setAlbumsSuccessfullyFetched()
        val firstEmission = repository.getPhotosForAlbum(1).first()
        assert(firstEmission is Result.Loading)
    }

    @Test
    fun getPhotosForAlbum_whenNotSuccessfull_firstReturnsLoading() = runBlockingTest{
        val repository = setErrorWhileFetchingAlbums()
        val firstEmission = repository.getPhotosForAlbum(1).first()
        assert(firstEmission is Result.Loading)
    }

    @Test
    fun getPhotosForAlbum_whenSuccessfull_returnSuccessWithSampleList() = runBlockingTest {
        val repository = setAlbumsSuccessfullyFetched()
        val secondEmission = repository.getPhotosForAlbum(1).drop(1).first()
        assertThat(secondEmission, `is`(Result.Success(mappedPhotoList)))
    }

    @Test
    fun getPhotosForAlbum_whenNotSuccessfull_returnError() = runBlockingTest {
        val repository = setErrorWhileFetchingAlbums()
        val secondEmission = repository.getPhotosForAlbum(1).drop(1).first()
        assert(secondEmission is Result.Error)
    }
}