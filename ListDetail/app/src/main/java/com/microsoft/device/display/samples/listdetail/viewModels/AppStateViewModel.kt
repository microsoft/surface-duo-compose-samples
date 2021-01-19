/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *  *
 *
 */

package com.microsoft.device.display.samples.companionpane.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppStateViewModel : ViewModel() {

    private val isScreenSpannedLiveData = MutableLiveData<Boolean>() // observe the screen spanning mode
    private val isScreenPortraitLiveData = MutableLiveData<Boolean>() // observe the screen Portrait/Landscape mode

    fun getIsScreenSpannedLiveData(): LiveData<Boolean> {
        return this.isScreenSpannedLiveData
    }

    fun setIsScreenSpannedLiveData(isScreenSpanned: Boolean) {
        isScreenSpannedLiveData.value = isScreenSpanned
    }
}
