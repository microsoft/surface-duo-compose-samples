/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.composesample.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppStateViewModel : ViewModel() {

    private val imageSelectionLiveData = MutableLiveData<Int>() // observe the image selection change
    private val isScreenSpannedLiveData = MutableLiveData<Boolean>() // observe the screen spanning mode

    fun getImageSelectionLiveData(): LiveData<Int> {
        return this.imageSelectionLiveData
    }

    fun setImageSelectionLiveData(selectedImage: Int) {
        imageSelectionLiveData.value = selectedImage
    }

    fun getIsScreenSpannedLiveData(): LiveData<Boolean> {
        return this.isScreenSpannedLiveData
    }

    fun setIsScreenSpannedLiveData(isScreenSpanned: Boolean) {
        isScreenSpannedLiveData.value = isScreenSpanned
    }
}
