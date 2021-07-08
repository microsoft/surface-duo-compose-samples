/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.listdetail.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppStateViewModel : ViewModel() {

    private val imageSelectionLiveData = MutableLiveData<Int>() // observe the image selection change
    private val isDualModeLiveData = MutableLiveData<Boolean>() // observe the dual-screen mode

    fun getIsDualModeLiveData(): LiveData<Boolean> {
        return this.isDualModeLiveData
    }

    fun setIsDualModeLiveData(isDualMode: Boolean) {
        isDualModeLiveData.value = isDualMode
    }

    fun getImageSelectionLiveData(): LiveData<Int> {
        return this.imageSelectionLiveData
    }

    fun setImageSelectionLiveData(selectedImage: Int) {
        imageSelectionLiveData.value = selectedImage
    }
}
