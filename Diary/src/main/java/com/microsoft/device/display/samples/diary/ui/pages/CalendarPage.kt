package com.microsoft.device.display.samples.diary.ui.pages

import android.view.ViewGroup
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.microsoft.device.display.samples.diary.R
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneScope
import java.time.LocalDate

/**
 *  The CalendarView Composable which also contains the Diary Content for the selected day
 */
@Composable
fun TwoPaneScope.CalendarPage(
    content: String,
    selectedDate: LocalDate,
    updateDate: (LocalDate) -> Unit,
    updateContent: () -> Unit
) {
    Scaffold(
        topBar = { CalendarTopBar() }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            AndroidView(
                {
                    CalendarView(it)
                        .apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                        }
                },
                modifier = Modifier.wrapContentWidth(),
                update = {
                    it.setOnDateChangeListener { _, year, month, dayOfMonth ->
                        val currentSelectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                        updateDate(currentSelectedDate)
                        updateContent()
                    }
                }

            )
            Spacer(
                modifier = Modifier
                    .background(color = Color.Gray)
                    .height(1.dp)
                    .fillMaxWidth()
            )
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
                modifier = Modifier.padding(10.dp),
                text = content,
                style = TextStyle(
                    fontSize = 18.sp,
                    textAlign = TextAlign.Left,
                )
            )
        }
    }
}

@Composable
fun TwoPaneScope.CalendarTopBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        actions = {
            if (this@CalendarTopBar.isSinglePane) {
                IconButton(onClick = { this@CalendarTopBar.navigateToPane2() }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_diary)
                    )
                }
            }
        }
    )
}
