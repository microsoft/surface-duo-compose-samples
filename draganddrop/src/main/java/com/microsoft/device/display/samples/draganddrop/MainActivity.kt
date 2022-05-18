package com.microsoft.device.display.samples.draganddrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.microsoft.device.display.samples.draganddrop.ui.theme.ComposeSamplesTheme
import com.microsoft.device.display.samples.draganddrop.ui.view.DragAndDropApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSamplesTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
                    DragAndDropApp()
                }
            }
        }
    }
}
