package com.canlioya.technicaltest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.canlioya.technicaltest.R
import com.canlioya.technicaltest.data.IRepository
import com.canlioya.technicaltest.model.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: IRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            repository.getAllAlbums().collect { result ->
                when (result) {
                    is Result.Loading -> Timber.d("loading")
                    is Result.Error -> Timber.d("network error")
                    is Result.Success -> {
                        Timber.d("success")
                        Timber.d("album list size: ${result.data?.size}")
                    }
                }
            }
        }
    }
}