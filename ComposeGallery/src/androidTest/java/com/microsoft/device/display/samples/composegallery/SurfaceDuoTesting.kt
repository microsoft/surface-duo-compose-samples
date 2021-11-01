/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.composegallery

enum class Device { Duo, Duo2 }

fun leftX(): Int {
    return 675
}

fun rightX(): Int {
    return 2109
}

fun middleX(): Int {
    return 1350
}

fun bottomY(device: Device): Int {
    return when (device) {
        Device.Duo -> 1780
        Device.Duo2 -> 1860
    }
}

fun middleY(): Int {
    return 900
}

fun spanSteps(): Int {
    return 400
}

fun unspanSteps(): Int {
    return 200
}

fun switchSteps(): Int {
    return 100
}

fun closeSteps(): Int {
    return 100
}
