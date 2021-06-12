package com.canlioya.technicaltest.ui.photos

import androidx.lifecycle.SavedStateHandle
import com.canlioya.technicaltest.data.mappedPhotoList
import com.canlioya.technicaltest.data.sampleAlbum1
import com.canlioya.technicaltest.fakedata.FailFakeRepository
import com.canlioya.technicaltest.fakedata.SuccessFakeRepository
import com.canlioya.technicaltest.model.UIState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

@ExperimentalCoroutinesApi
class PhotoViewModelTest {

    private var testDispatcher = TestCoroutineDispatcher()

    fun setForSuccessfulResult() : PhotoViewModel {
        val args = mutableMapOf<String, Any>("chosenAlbum" to sampleAlbum1)
        val dummySavedStateHandler = SavedStateHandle(args)
        return PhotoViewModel(dummySavedStateHandler, SuccessFakeRepository(), testDispatcher)
    }

    fun setForError() : PhotoViewModel {
        val args = mutableMapOf<String, Any>("chosenAlbum" to sampleAlbum1)
        val dummySavedStateHandler = SavedStateHandle(args)
        return PhotoViewModel(dummySavedStateHandler, FailFakeRepository(), testDispatcher)
    }

    @Test
    fun startFetching_successful_albumListReturns() = runBlockingTest{
        val viewModel = setForSuccessfulResult()
        viewModel.startFetching()
        val firstEmission = viewModel.photos.first()
        MatcherAssert.assertThat(firstEmission, CoreMatchers.`is`(mappedPhotoList))
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
