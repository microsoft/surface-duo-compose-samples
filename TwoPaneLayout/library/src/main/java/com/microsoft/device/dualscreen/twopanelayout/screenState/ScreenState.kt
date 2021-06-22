/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.dualscreen.twopanelayout.screenState

import android.graphics.Rect
import androidx.compose.ui.geometry.Size

/**
 * LayoutOrientation
 *     Horizontal,  the width of pane is bigger than the height
 *     Vertical     the height of pane is bigger than the width
 */
enum class LayoutOrientation {
    Horizontal,
    Vertical
}

/**
 * LayoutState
 * Open,        multiple layout display, it is always "Open" for big-screen device
 * Fold         single layout display, including single-screen phone, foldable device in folding mode and app in un-spanned mode
 */
enum class LayoutState {
    Open,
    Fold
}

/**
 * DeviceType
 *     Single,    // regular single-screen device, such as single-screen phone
 *     Multiple,  // dual-screen/foldable/rollable device, such as Surface Duo device, Samsung Galaxy Fold 2
 *     Big        // big-screen device, such as tablet
 */
enum class DeviceType {
    Single,
    Multiple,
    Big
}

class ScreenState(
    val deviceType: DeviceType,
    val screenSize: Size,
    var hingeBounds: Rect,
    var orientation: LayoutOrientation,
    var layoutState: LayoutState
) {
    var paneSize: Size = Size.Zero
        get() {
            if (deviceType == DeviceType.Big) {
                return if (orientation == LayoutOrientation.Vertical) {
                    Size(width = screenSize.width / 2, height = screenSize.height)
                } else {
                    Size(width = screenSize.width, height = screenSize.height / 2)
                }
            } else if (deviceType == DeviceType.Multiple) {
                return if (orientation == LayoutOrientation.Vertical) {
                    Size(
                        width = hingeBounds.left.toFloat(),
                        height = hingeBounds.height().toFloat()
                    )
                } else {
                    Size(
                        width = hingeBounds.width().toFloat(),
                        height = hingeBounds.top.toFloat()
                    )
                }
            }
            return screenSize
        }
}
