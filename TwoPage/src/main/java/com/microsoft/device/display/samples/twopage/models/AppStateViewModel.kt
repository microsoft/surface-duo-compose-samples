/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppStateViewModel : ViewModel() {

    private val isDualModeLiveData = MutableLiveData<Boolean>() // observe the dual-screen mode
    var screenWidth: Int = 0
    var hingeWidth: Int = 0

    fun getIsDualModeLiveDataLiveData(): LiveData<Boolean> {
        return this.isDualModeLiveData
    }

    fun setIsDualModeLiveDataLiveData(isDualMode: Boolean) {
        isDualModeLiveData.value = isDualMode
    }
}
