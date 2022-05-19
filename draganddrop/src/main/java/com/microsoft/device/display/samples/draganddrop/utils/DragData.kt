/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop.utils

class DragData(
    type: MimeType = MimeType.TEXT_PLAIN,
    data: Any? = null
) {
    val type = type
    val data = data
}
