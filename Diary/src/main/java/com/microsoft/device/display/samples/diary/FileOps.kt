package com.microsoft.device.display.samples.diary

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

fun readDayFile(fileName: String, rootDir: File, context: Context): String {
    val file = File(rootDir, "/$fileName")
    if (!file.exists()) {
        return context.getString(R.string.no_diary_content)
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
