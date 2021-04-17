package com.microsoft.device.display.twopanelayout.sample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.window.DisplayFeature
import androidx.window.FoldingFeature
import androidx.window.WindowManager
import com.microsoft.device.display.twopanelayout.sample.ui.theme.TwoPaneLayoutTheme
import java.util.concurrent.Executor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwoPaneLayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainPage()
                }
            }
        }
    }
}

@Composable
fun MainPage() {
    println("############### MainPage")
    SideEffect {
        println("############### SideEffect")
    }
    LaunchedEffect(true) {
        println("############### LaunchedEffect")
    }

    DisposableEffect(true) {
        // Add callback to the backDispatcher
        println("############### DisposableEffect")

        // When the effect leaves the Composition, remove the callback
        onDispose {
            println("############### DisposableEffect: onDispose")
        }
    }
}

@Composable
fun ScreenStateProvider() {
    val context = LocalContext.current
    val windowManager = WindowManager(context)
    val handler = Handler(Looper.getMainLooper())
    val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }
    windowManager.registerLayoutChangeCallback(
        mainThreadExecutor,
        { windowLayoutInfo ->
            reserveScreenState(windowLayoutInfo.displayFeatures)
        }
    )
}

private fun reserveScreenState(displayFeatures: List<DisplayFeature>) {
    val isScreenSpanned = displayFeatures.isNotEmpty()
    if (isScreenSpanned) {
        val foldingFeature = displayFeatures.first() as FoldingFeature

    }
}
