/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.chat.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppStateViewModel @Inject constructor()
: ViewModel() {

    private val isDualModeLiveData = MutableLiveData<Boolean>() // observe the dual-screen mode
    var displayChatDetails by mutableStateOf(false)

    fun getIsDualModeLiveDataLiveData(): LiveData<Boolean> {
        return this.isDualModeLiveData
    }

    fun setIsDualModeLiveDataLiveData(isDualMode: Boolean) {
        isDualModeLiveData.value = isDualMode
    }
}
