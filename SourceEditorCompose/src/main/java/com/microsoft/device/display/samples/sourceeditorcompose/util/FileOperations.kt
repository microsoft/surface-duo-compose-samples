/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.sourceeditorcompose.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

class FileOperations {
    fun readFile(file: String, context: Context?): String {
        return BufferedReader(InputStreamReader(context?.assets?.open(file))).useLines { lines ->
            val results = StringBuilder()
            lines.forEach { results.append(it + System.getProperty("line.separator")) }
            results.toString()
        }
    }
}
