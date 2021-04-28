package com.microsoft.device.display.twopanelayout.screenState

import android.graphics.Rect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size

enum class LayoutOrientation {
    Horizontal,  // the width of pane is bigger than the height
    Vertical     // the height of pane is bigger than the width
}

enum class LayoutState {
    Open,        // multiple layout display, it is always "Open" for big-screen device
    Fold         // single layout display, including single-screen phone, foldable device in folding mode and app in un-spanned mode
}

enum class DeviceType {
    Single,    // regular single-screen device, such as single-screen phone
    Multiple,  // dual-screen/foldable/rollable device, such as Surface Duo device, Samsung Galaxy Fold 2
    Big        // big-screen device, such as tablet
}

class ScreenState(
    val deviceType: DeviceType,
    val screenSize: Size,
    var hingeBounds: Rect,
    var orientation: LayoutOrientation,
    var layoutState: LayoutState
) {
    var paneSizes: List<Size> = emptyList()
        get() {
            // TODO: calculate size by weight
            if (deviceType == DeviceType.Big) { // TODO: split into only 2 panes for tablet for now
                return if (orientation == LayoutOrientation.Vertical) {
                    val paneSize = Size(width = screenSize.width/2, height = screenSize.height)
                    listOf(paneSize, paneSize)
                } else {
                    val paneSize = Size(width = screenSize.width, height = screenSize.height/2)
                    listOf(paneSize, paneSize)
                }
            } else if (deviceType == DeviceType.Multiple) { // TODO: only support dual-screen for now
                return if (orientation == LayoutOrientation.Vertical) {
                    val paneSize = Size(
                        width = hingeBounds.left.toFloat(),
                        height = hingeBounds.height().toFloat()
                    )
                    listOf(paneSize, paneSize)
                } else {
                    val paneSize = Size(
                        width = hingeBounds.width().toFloat(),
                        height = hingeBounds.top.toFloat()
                    )
                    listOf(paneSize, paneSize)
                }
            }
            return listOf(screenSize)
        }

    var hingeWidth: Int = 0
        get() {
            if (deviceType == DeviceType.Multiple) {
                return when (orientation) {
                    LayoutOrientation.Vertical -> hingeBounds.width()
                    LayoutOrientation.Horizontal -> hingeBounds.height()
                }
            }
            return 0
        }
}
