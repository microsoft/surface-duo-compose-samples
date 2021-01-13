/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.composesample.models

import com.microsoft.device.display.samples.composesample.R
import java.util.ArrayList

object DataProvider {
    val imageModels: ArrayList<ImageModel>
        get() {
            val items = ArrayList<ImageModel>()
            items.add(ImageModel("Surface Duo 1", "This is the first image", image = R.drawable.surface_duo_1, imageId = 1))
            items.add(ImageModel("Surface Duo 2", "This is the second image", image = R.drawable.surface_duo_2, imageId = 2))
            items.add(ImageModel("Surface Duo 3", "This is the third image", image = R.drawable.surface_duo_3, imageId = 3))
            items.add(ImageModel("Surface Duo 4", "This is the fourth image", image = R.drawable.surface_duo_4, imageId = 4))
            items.add(ImageModel("Surface Duo 5", "This is the fifth image", image = R.drawable.surface_duo_5, imageId = 5))
            items.add(ImageModel("Surface Duo 6", "This is the sixth image", image = R.drawable.surface_duo_6, imageId = 6))
            items.add(ImageModel("Surface Duo 7", "This is the seventh image", image = R.drawable.surface_duo_7, imageId = 7))
            items.add(ImageModel("Surface Duo 8", "This is the eighth image", image = R.drawable.surface_duo_8, imageId = 8))
            return items
        }
}
