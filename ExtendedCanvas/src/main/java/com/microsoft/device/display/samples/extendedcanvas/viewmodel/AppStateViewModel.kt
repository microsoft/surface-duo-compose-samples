package com.microsoft.device.display.samples.extendedcanvas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppStateViewModel : ViewModel() {

    private val isScreenPortraitLiveData = MutableLiveData<Boolean>() // observe the screen Portrait/Landscape mode

    fun getIsScreenPortraitLiveData(): LiveData<Boolean> {
        return this.isScreenPortraitLiveData
    }

    fun setIsScreenPortraitLiveData(IsScreenPortrait: Boolean) {
        isScreenPortraitLiveData.value = IsScreenPortrait
    }
}
