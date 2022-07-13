package com.microsoft.device.display.samples.diary.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.diary.R
import com.microsoft.device.display.samples.diary.saveFile
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneScope
import java.io.File
import java.time.LocalDate

/**
 *  EditorPage Composable contains the Editor for the Diary for selected date on the CalendarView
 */
@Composable
fun TwoPaneScope.DiaryPage(
    text: String,
    updateText: (String) -> Unit,
    selectedDate: LocalDate,
    updateContent: () -> Unit
) {
    val twoPaneScope = this
    val context = LocalContext.current
    val rootDataDir: File = context.applicationContext.dataDir

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSinglePane) {
                        Text(text = stringResource(R.string.app_name))
                    }
                },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = Color.White,
                actions = {
                    if (twoPaneScope.isSinglePane) {
                        IconButton(
                            onClick = { twoPaneScope.navigateToPane1() }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.DateRange,
                                contentDescription = "Date Range Icon"
                            )
                        }
                    }
                }
            )
        }
    ) {
        Column {
            Row(modifier = Modifier.weight(0.9f)) {
                TextField(
                    value = text,
                    placeholder = { Text(stringResource(R.string.diary_placeholder)) },
                    onValueChange = { newText -> updateText(newText) },
                    modifier = Modifier.fillMaxSize(),

                    )
            }
            Row(
                modifier = Modifier
                    .weight(0.1f)
            ) {
                Button(
                    modifier = Modifier
                        .width(150.dp)
                        .padding(all = 10.dp),
                    onClick = {
                        saveFile(selectedDate.toString(), text, rootDataDir)
                        updateContent()
                    }
                ) {
                    Text(text = stringResource(R.string.save_button))
                }
            }
        }
    }
}
