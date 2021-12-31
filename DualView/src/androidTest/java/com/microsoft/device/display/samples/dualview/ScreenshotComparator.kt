/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.dualview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import androidx.core.graphics.toColor
import androidx.test.platform.app.InstrumentationRegistry
import java.io.FileOutputStream
import kotlin.math.abs

/**
 * Based on ScreenshotComparator.kt in the TestingCodelab project from the official Jetpack Compose codelab samples
 * https://github.com/googlecodelabs/android-compose-codelabs/blob/main/TestingCodelab/app/src/androidTest/java/com/example/compose/rally/ScreenshotComparator.kt
 * TODO: move to testing utils folder when finalized
 */

@RequiresApi(Build.VERSION_CODES.O)
fun assertScreenshotMatchesReference(
    referenceAsset: String,
    node: SemanticsNodeInteraction
) {
    // Capture screenshot of composable
    val bitmap = node.captureToImage().asAndroidBitmap()

    // Load reference screenshot from instrumentation test assets
    val referenceBitmap = InstrumentationRegistry.getInstrumentation().context.resources.assets.open(referenceAsset)
        .use { BitmapFactory.decodeStream(it) }

    // Compare bitmaps
    assert(referenceBitmap.compare(bitmap))
}

fun saveScreenshotToDevice(filename: String, node: SemanticsNodeInteraction) {
    // Capture screenshot of composable
    val bmp = node.captureToImage().asAndroidBitmap()

    // Get path for saving file
    val path = InstrumentationRegistry.getInstrumentation().targetContext.filesDir.canonicalPath

    // Compress bitmap and send to file (can be retrieved via adb)
    // ADB instructions: https://stackoverflow.com/questions/40323126/where-do-i-find-the-saved-image-in-android
    FileOutputStream("$path/$filename").use { out ->
        bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    println("Saved screenshot to $path/$filename")
}

fun Bitmap.compare(other: Bitmap): Boolean {
    if (this.width != other.width || this.height != other.height) {
        throw AssertionError("Size of screenshot does not match golden file. Expected: $width $height Actual: ${other.width} ${other.height}")
    }

    // Compare row by row to save memory on device
    val row1 = IntArray(width)
    val row2 = IntArray(width)
    var numDiffs = 0

    for (column in 0 until height) {
        // Read one row per bitmap and compare
        this.getRow(row1, column)
        other.getRow(row2, column)
        row1.forEachIndexed { index, element ->
            if (!row2[index].isSimilarColor(element)) {
                numDiffs++
            }
        }
    }

    // Throw error if greater than 1% of the bitmap's pixels are different
    if (numDiffs > 0.01 * width * width) {
        Log.d("Screen Comparator", "Sizes match but bitmap content has differences. Num diffs: $numDiffs")
        return false
    }
    Log.d("Screen Comparator", "Number of different pixels: $numDiffs")
    return true
}

private fun Int.isSimilarColor(other: Int): Boolean {
    // Convert ints to color values
    val expectedColor = this.toColor()
    val actualColor = other.toColor()

    // Compare individual color components
    val expectedComponents = expectedColor.components
    val actualComponents = actualColor.components

    if (expectedComponents.size != actualComponents.size)
        Log.d(
            "Screen Comparator",
            "Difference in color components size. Expected: ${expectedComponents.size} Actual: ${actualComponents.size}"
        )

    val percentError = 0.005
    expectedComponents.forEachIndexed { index, comp ->
        // Calculate the error allowance for the color component
        val maxColorVal = expectedColor.colorSpace.getMaxValue(index)
        val minColorVal = expectedColor.colorSpace.getMinValue(index)
        val errorAllowance = percentError * (maxColorVal - minColorVal)

        // Compare color component values
        if (abs(actualComponents[index] - comp) > errorAllowance) {
            Log.d(
                "Screen Comparator",
                "Colors are not similar for component $index Expected: $comp Actual: ${actualComponents[index]}"
            )
            return false
        }
    }
    return true
}

private fun Bitmap.getRow(pixels: IntArray, column: Int) {
    this.getPixels(pixels, 0, width, 0, column, width, 1)
}
