package com.example.videochatcomposesample.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode

class InfoProvider {

    var paneMode: TwoPaneMode by (mutableStateOf(TwoPaneMode.TwoPane))
        private set

    var isFullScreen: Boolean by (mutableStateOf(paneMode == TwoPaneMode.VerticalSingle))

    val isKeyboardOpen
        @Composable get() = keyboardAsState()

    val keyBoardToggle
        @Composable get() = isKeyboardOpen.value === Keyboard.Opened

    fun updatePaneMode(newPaneMode: TwoPaneMode) = run {
        paneMode = newPaneMode
        isFullScreen = (paneMode == TwoPaneMode.VerticalSingle)
    }
}
