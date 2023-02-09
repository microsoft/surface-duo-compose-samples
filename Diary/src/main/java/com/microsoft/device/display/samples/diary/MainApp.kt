package com.microsoft.device.display.samples.diary

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.microsoft.device.display.samples.diary.ui.pages.CalendarPage
import com.microsoft.device.display.samples.diary.ui.pages.DiaryPage
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import java.io.File
import java.time.LocalDate

/**
 *  For handling window navigation for Surface Duo, this acts as a main Controller for both the Composable
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp() {
    val context = LocalContext.current
    val rootDataDir: File = context.applicationContext.dataDir

    var text by rememberSaveable { mutableStateOf("") }
    var currentSelectedDate by rememberSaveable {
        mutableStateOf(LocalDate.now())
    }
    var content by rememberSaveable { mutableStateOf("") }

    val updateDate: (LocalDate) -> Unit = { date ->
        currentSelectedDate = date
    }
    val updateContent: () -> Unit = {
        content = readDayFile(currentSelectedDate.toString(), rootDataDir, context)
    }
    val updateText: (String) -> Unit = { newText ->
        text = newText
    }

    TwoPaneLayout(
        pane1 = {
            CalendarPage(
                content = content,
                selectedDate = currentSelectedDate,
                updateDate = updateDate,
                updateContent = updateContent
            )
        },
        pane2 = {
            DiaryPage(
                text = text,
                updateText = updateText,
                selectedDate = currentSelectedDate,
                updateContent = updateContent
            )
        }
    )
}
