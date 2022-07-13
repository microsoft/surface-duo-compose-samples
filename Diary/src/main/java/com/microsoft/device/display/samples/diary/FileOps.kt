package com.microsoft.device.display.samples.diary


import android.content.ContentResolver
import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

fun readDayFile(fileName: String, rootDir: File): String {
    val file = File(rootDir, "/$fileName")
    val noContent: String = R.string.no_diary_content.toString()
    if (!file.exists()) {
        return noContent
    }
    val fileReader = FileReader(file)

    val dayContent = BufferedReader(fileReader).useLines { lines ->
        val content = StringBuilder()
        lines.forEach { content.append(it + System.getProperty("line.separator")) }
        content.toString()
    }
    fileReader.close()
    return dayContent
}

/**
 *  To save a txt file to a specified file path
 */
fun saveFile(fileName: String, content: String, rootDir: File) {
    val file = File(rootDir, "/$fileName")
    val out = FileWriter(file)
    try {
        out.write(content)
        out.close()
    } catch (ioError: IOException) {
        Log.d("TAG", "saveFile: IO ERROR -> $ioError")
    }
}
