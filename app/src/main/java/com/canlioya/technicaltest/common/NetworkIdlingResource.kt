package com.canlioya.technicaltest.common

import androidx.test.espresso.idling.CountingIdlingResource

class NetworkIdlingResource {

    companion object {
        val resource = "networkResource"
        val countingIdlingResource = CountingIdlingResource(resource)
    }

    fun increment(){
        countingIdlingResource.increment()
    }

    fun decrement(){
        if(!countingIdlingResource.isIdleNow){
            countingIdlingResource.decrement()
        }
    }
}