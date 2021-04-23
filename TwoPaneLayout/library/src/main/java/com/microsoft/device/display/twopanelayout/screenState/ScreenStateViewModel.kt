package com.microsoft.device.display.twopanelayout.screenState.model

import android.graphics.Rect
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class LayoutOrientation {
    Horizontal,
    Vertical
}

enum class LayoutState {
    Open,
    Fold
}

data class ScreenState(
    val isTablet: Boolean,
    val featureBounds: Rect,
    val layoutState: LayoutState,
    val orientation: LayoutOrientation
)

class ScreenStateViewModel : ViewModel() {
    private var _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState>
        get() = _screenState

    fun setScreenState(
        isTablet: Boolean,
        featureBounds: Rect,
        layoutState: LayoutState,
        orientation: LayoutOrientation
    ) {
        _screenState.value = ScreenState(
            isTablet = isTablet,
            featureBounds = featureBounds,
            layoutState = layoutState,
            orientation = orientation
        )
    }
}