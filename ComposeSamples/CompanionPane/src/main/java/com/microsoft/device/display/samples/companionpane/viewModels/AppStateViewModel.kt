/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.companionpane.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class ScreenState {
    SinglePortrait,
    SingleLandscape,
    DualPortrait,
    DualLandscape
}

class AppStateViewModel : ViewModel() {

    private val screenStateLiveData = MutableLiveData<ScreenState>() // observe the screen state

    fun getScreenStateLiveData(): LiveData<ScreenState> {
        return this.screenStateLiveData
    }

    fun setScreenStateLiveData(screenState: ScreenState) {
        screenStateLiveData.value = screenState
    }
}
