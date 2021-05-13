/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.twopanelayout.screenState

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScreenStateViewModel : ViewModel() {
    var screenStateLiveData = MutableLiveData<ScreenState>()
}
