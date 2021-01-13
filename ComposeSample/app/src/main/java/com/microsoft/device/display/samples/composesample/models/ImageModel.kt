/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.composesample.models

data class ImageModel(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val image: Int = 0,
    val imageId: Int = 0
)
