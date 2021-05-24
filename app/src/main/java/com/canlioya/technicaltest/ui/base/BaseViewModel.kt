package com.canlioya.technicaltest.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.canlioya.technicaltest.model.UIState

/**
 * Base viewmodel class to be extended by
 * [AlbumViewModel] and [PhotoViewModel].
 * Facilitates usage in BaseFragment
 *
 * @constructor Create empty Base view model
 */
abstract class BaseViewModel : ViewModel() {

    protected val _uiState: MutableLiveData<UIState> = MutableLiveData()

    /**
     * Backed by private MutableLiveData _uiState of type
     * {@link com.canlioya.technicaltest.model.UIState}
     * and exposed as immutable LiveData
     */
    val uiState: LiveData<UIState>
        get() = _uiState

    /**
     * abstract method to be overridden by inheritors
     * for starting fetching from the internet
     */
    abstract fun startFetching()
}