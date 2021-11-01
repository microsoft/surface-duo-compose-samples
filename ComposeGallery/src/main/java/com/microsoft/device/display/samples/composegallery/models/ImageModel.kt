/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery.models

import androidx.annotation.DrawableRes

data class ImageModel(
    val id: String,
    val description: String,
    val contentDescription: String? = null,
    @DrawableRes val image: Int,
    val imageId: Int
)
