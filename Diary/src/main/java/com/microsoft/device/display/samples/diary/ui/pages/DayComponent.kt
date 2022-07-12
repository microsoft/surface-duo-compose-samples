package com.microsoft.device.display.samples.diary.ui.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.display.samples.diary.FileOps
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneScope
import java.io.File
import java.time.LocalDate

@Composable
fun TwoPaneScope.DayComponent(
    content: String,
    text: String,
    selectedDate: LocalDate,
    updateDate: (LocalDate) -> Unit,
    updateContent: () -> Unit
) {

    val context = LocalContext.current
    val rootDataDir: File = context.applicationContext.dataDir
    val fileOps: FileOps = FileOps()
    val diaryToday: String = fileOps.readDayFile(selectedDate.toString(), rootDataDir, context)

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp),
        text = selectedDate.toString(),
        style = TextStyle(
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            shadow = Shadow(
                color = Color.Gray,
                offset = Offset(4.0f, 5.0f),
                blurRadius = 2f
            )
        )
    )

    Text(
        text = content,
        Modifier.padding(10.dp),
        style = TextStyle(
            fontSize = 18.sp,
            textAlign = TextAlign.Left,
        )
    )
}
