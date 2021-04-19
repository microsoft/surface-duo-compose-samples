/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class ScreenState {
    SingleScreen,
    DualPortrait,
    DualLandscape
}

class AppStateViewModel : ViewModel() {

    var viewWidth: Int = 0

    private val screenStateLiveData = MutableLiveData<ScreenState>() // observe the screen state
    private val selectionLiveData = MutableLiveData<Int>() // observe the image selection change

    fun getScreenStateLiveDataLiveData(): LiveData<ScreenState> {
        return this.screenStateLiveData
    }

    fun setScreenStateLiveData(screenState: ScreenState) {
        screenStateLiveData.value = screenState
    }

    fun getSelectionLiveData(): LiveData<Int> {
        return this.selectionLiveData
    }

    fun setSelectionLiveData(selectedImage: Int) {
        selectionLiveData.value = selectedImage
    }
}
