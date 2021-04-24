package com.microsoft.device.display.twopanelayout.screenState

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScreenStateViewModel : ViewModel() {
    private var _screenStateLiveData = MutableLiveData<ScreenState>()
    val screenStateLiveData: LiveData<ScreenState>
        get() = _screenStateLiveData

    fun setScreenState(screenState: ScreenState) {
        _screenStateLiveData.value = screenState
    }
}
