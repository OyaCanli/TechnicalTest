package com.canlioya.technicaltest.ui.albums

import com.canlioya.technicaltest.fakedata.FailFakeRepository
import com.canlioya.technicaltest.fakedata.SuccessFakeRepository
import com.canlioya.technicaltest.data.mappedAlbumList
import com.canlioya.technicaltest.model.UIState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumViewModelTest {

    private var testDispatcher = TestCoroutineDispatcher()

    fun setForSuccessfulResult() : AlbumViewModel{
        return AlbumViewModel(SuccessFakeRepository(), testDispatcher)
    }

    fun setForError() : AlbumViewModel{
        return AlbumViewModel(FailFakeRepository(), testDispatcher)
    }

    @Test
    fun startFetching_successful_albumListReturns() = runBlockingTest{
        val viewModel = setForSuccessfulResult()
        viewModel.startFetching()
        val firstEmission = viewModel.albums.first()
        assertThat(firstEmission, `is`(mappedAlbumList))
    }

    @Test
    fun startFetching_successful_uiStateIsSuccess() = runBlockingTest{
        val viewModel = setForSuccessfulResult()
        viewModel.startFetching()
        val firstEmission = viewModel.uiState.first()
        assert(firstEmission == UIState.SUCCESS)
    }

    @Test
    fun startFetching_error_uiStateIsError() = runBlockingTest{
        val viewModel = setForError()
        viewModel.startFetching()
        val firstEmission = viewModel.uiState.first()
        assert(firstEmission == UIState.ERROR)
    }
}