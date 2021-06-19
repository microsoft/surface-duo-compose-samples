package com.microsoft.device.display.samples.chat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.window.DisplayFeature
import androidx.window.FoldingFeature
import androidx.window.WindowManager
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.microsoft.device.display.samples.chat.models.DataProvider
import com.microsoft.device.display.samples.chat.ui.ChatComposeSamplesTheme
import com.microsoft.device.display.samples.chat.viewModels.AppStateViewModel
import java.util.concurrent.Executor

class MainActivity : ComponentActivity() {

    private lateinit var windowManager: WindowManager
    private lateinit var appStateViewModel: AppStateViewModel

    private val handler = Handler(Looper.getMainLooper())
    private val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }

    override fun onCreate(savedInstanceState: Bundle?) {
        windowManager = WindowManager(this)
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContent {
            ChatComposeSamplesTheme {
                val systemUiController = rememberSystemUiController()
                val isDualModeLiveDataLiveData = appStateViewModel.getIsDualModeLiveDataLiveData()
                val isDualMode = isDualModeLiveDataLiveData.observeAsState(initial = false).value
                val models = DataProvider.contactModels
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.White,
                    )
                    systemUiController.setNavigationBarColor(
                        color = Color(0xFFF8F8F8)
                    )
                }
                SetupUI(viewModel = appStateViewModel)
                if (!isDualMode) {
                    ChatDetails(models, appStateViewModel)
                }
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
