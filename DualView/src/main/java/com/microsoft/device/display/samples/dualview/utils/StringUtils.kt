/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun formatRating(rating: Double, voteCount: Int): String {
    val builder = StringBuilder()
    builder.append(rating)
    builder.append(" (")
    builder.append(addThousandSeparator(voteCount))
    builder.append(')')
    return builder.toString()
}

fun formatPriceRange(range: Int): String {
    val builder = StringBuilder()
    for (index in 1..range) {
        builder.append('$')
    }
    return builder.toString()
}

fun addThousandSeparator(number: Int): String {
    val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("#,###")
    return formatter.format(number)
}
