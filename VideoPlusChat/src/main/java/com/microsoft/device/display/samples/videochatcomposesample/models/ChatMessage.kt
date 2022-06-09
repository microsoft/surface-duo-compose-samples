/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.videochatcomposesample.models

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class ChatMessage(@StringRes val author: Int, @StringRes val message: Int, val color: Color)