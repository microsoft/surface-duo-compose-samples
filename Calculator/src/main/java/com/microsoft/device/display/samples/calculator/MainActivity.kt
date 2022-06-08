package com.microsoft.device.display.samples.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.microsoft.device.display.samples.calculator.ui.pages.MainPage
import com.microsoft.device.display.samples.calculator.ui.theme.CalculatorTheme
import com.microsoft.device.dualscreen.windowstate.WindowState
import com.microsoft.device.dualscreen.windowstate.rememberWindowState

class MainActivity : ComponentActivity() {
    private lateinit var windowState: WindowState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            windowState = rememberWindowState()
            CalculatorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainPage(windowState = windowState)
                }
            }
        }
    }
}
