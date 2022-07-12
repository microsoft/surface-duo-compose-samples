package com.microsoft.device.display.samples.diary

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class FileOps {

    fun readDayFile(fileName: String, rootDir: File): String {
        val file = File(rootDir, "/$fileName")

        if (!file.exists()) {
            return "Let's start writing for the day"
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

    /**
     *  To check if device has Storage READ WRITE and MANAGE permissions
     */
    fun checkPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val result =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            val result1 =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
        }
    }
}
