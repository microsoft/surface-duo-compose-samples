package com.microsoft.device.display.samples.chat

import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.window.DisplayFeature
import androidx.window.FoldingFeature
import androidx.window.WindowManager
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.microsoft.device.display.samples.chat.ui.ChatComposeSamplesTheme
import com.microsoft.device.display.samples.chat.viewModels.AppStateViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.Executor

@HiltAndroidApp
class Chat : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var windowManager: WindowManager
    private lateinit var appStateViewModel: AppStateViewModel
    private val handler = Handler(Looper.getMainLooper())
    private val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        windowManager = WindowManager(this)
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)

        setContent {
            ChatComposeSamplesTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.White,
                    )
                    systemUiController.setNavigationBarColor(
                        color = Color(0xFFF8F8F8)
                    )
                }
                SetupUI()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        windowManager.registerLayoutChangeCallback(
            mainThreadExecutor,
            { windowLayoutInfo ->
                reserveScreenState(windowLayoutInfo.displayFeatures)
            }
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        windowManager.unregisterLayoutChangeCallback {}
    }

    private fun reserveScreenState(displayFeatures: List<DisplayFeature>) {
        var isDualMode = false

        val isScreenSpanned = displayFeatures.isNotEmpty()
        if (isScreenSpanned) {
            val foldingFeature = displayFeatures.first() as FoldingFeature
            val isVertical = foldingFeature.orientation == FoldingFeature.ORIENTATION_VERTICAL
            isDualMode = isVertical
        }

        appStateViewModel.setIsDualModeLiveDataLiveData(isDualMode)
    }
}
