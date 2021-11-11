/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppStateViewModel : ViewModel() {

    var viewWidth: Int = 0
    var selectionLiveData = MutableLiveData<Int>(-1) // observe the image selection change
}
