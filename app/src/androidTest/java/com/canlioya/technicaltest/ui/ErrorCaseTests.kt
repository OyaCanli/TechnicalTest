package com.canlioya.technicaltest.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.canlioya.technicaltest.R
import com.canlioya.technicaltest.common.NetworkIdlingResource
import com.canlioya.technicaltest.data.FailingRetrofitService
import com.canlioya.technicaltest.data.SuccessfullRetrofitService
import com.canlioya.technicaltest.data.network.AlbumApiService
import com.canlioya.technicaltest.data.sampleAlbum1
import com.canlioya.technicaltest.di.NetworkModule
import com.canlioya.technicaltest.testutils.DataBindingIdlingResource
import com.canlioya.technicaltest.testutils.monitorActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton

@RunWith(AndroidJUnit4::class)
@LargeTest
@ExperimentalCoroutinesApi
@UninstallModules(NetworkModule::class)
@HiltAndroidTest
class ErrorCaseTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // An Idling Resource that waits for Data Binding to have no pending bindings.
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
        IdlingRegistry.getInstance().register(NetworkIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource.countingIdlingResource)
    }

    @Test
    fun atLaunch_networkErrorImageIsShown() = runBlocking {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Verify that list is displayed and not empty
        onView(withId(R.id.error_image)).check(matches(isDisplayed()))
        onView(withId(R.id.list)).check(doesNotExist())

        activityScenario.close()
    }

    @Module
    @InstallIn(ApplicationComponent::class)
    object FakeErrorNetworkModule {

        @Singleton
        @Provides
        fun provideFakeSuccessfullService() : AlbumApiService = FailingRetrofitService()
    }
}