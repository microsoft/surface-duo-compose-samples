package com.microsoft.device.display.twopanelayout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class ScreenState(
    val isSpanned: Boolean,
    val isTablet: Boolean,
    val isVertical: Boolean,
    val hingeWidth: Int)

class ScreenStateViewModel : ViewModel() {

    private val screenStateLiveData = MutableLiveData<ScreenState>()

    fun getScreenStateLiveData(): LiveData<ScreenState> {
        return this.screenStateLiveData
    }

    fun setScreenStateLiveData(screenState: ScreenState) {
        screenStateLiveData.value = screenState
    }

}