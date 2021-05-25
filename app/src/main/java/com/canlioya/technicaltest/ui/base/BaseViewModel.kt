package com.canlioya.technicaltest.ui.base

import androidx.lifecycle.ViewModel
import com.canlioya.technicaltest.model.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Base viewmodel class to be extended by
 * [AlbumViewModel] and [PhotoViewModel].
 * Facilitates usage in BaseFragment
 *
 * @constructor Create empty Base view model
 */
abstract class BaseViewModel : ViewModel() {

    protected val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.LOADING)

    /**
     * Backed by private MutableLiveData _uiState of type
     * {@link com.canlioya.technicaltest.model.UIState}
     * and exposed as immutable LiveData
     */
    val uiState: StateFlow<UIState>
        get() = _uiState

    /**
     * abstract method to be overridden by inheritors
     * for starting fetching from the internet
     */
    abstract suspend fun startFetching()
}