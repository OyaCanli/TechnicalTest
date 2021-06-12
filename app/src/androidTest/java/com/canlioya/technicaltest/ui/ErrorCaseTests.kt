package com.canlioya.technicaltest.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.canlioya.technicaltest.R
import com.canlioya.technicaltest.common.NetworkIdlingResource
import com.canlioya.technicaltest.data.FailingRetrofitService
import com.canlioya.technicaltest.data.network.AlbumApiService
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

    @Module
    @InstallIn(ApplicationComponent::class)
    object FakeErrorNetworkModule {

        @Singleton
        @Provides
        fun provideFakeSuccessfullService() : AlbumApiService = FailingRetrofitService()

        //This is only for instrumentation testing, it can be null on production code
        @Provides
        fun provideIdlingResource() : NetworkIdlingResource = NetworkIdlingResource()
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // An Idling Resource that waits for Data Binding to have no pending bindings.
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun init() {
        // Populate @Inject fields in test class
        hiltRule.inject()
    }

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

        //Verify that error image is shown
        onView(withId(R.id.error_image)).check(matches(isDisplayed()))

        activityScenario.close()
    }
}