package com.microsoft.device.display.samples.dualview

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.window.WindowManager
import com.microsoft.device.display.samples.dualview.models.AppStateViewModel
import com.microsoft.device.display.samples.dualview.ui.theme.DualViewComposeSampleTheme
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    private lateinit var windowManager: WindowManager
    private lateinit var appStateViewModel: AppStateViewModel

    private val handler = Handler(Looper.getMainLooper())
    private val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }

    override fun onCreate(savedInstanceState: Bundle?) {
        windowManager = WindowManager(this, null)
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)
        val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        appStateViewModel.setIsScreenPortraitLiveData(isPortrait)

        super.onCreate(savedInstanceState)
        setContent {
            DualViewComposeSampleTheme {
                SetupUI(appStateViewModel)
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        windowManager.registerLayoutChangeCallback(
            mainThreadExecutor,
            { windowLayoutInfo ->
                appStateViewModel.setIsScreenSpannedLiveData(windowLayoutInfo.displayFeatures.size > 0)
            }
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        windowManager.unregisterLayoutChangeCallback {}
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val isPortrait = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT
        appStateViewModel.setIsScreenPortraitLiveData(isPortrait)
    }
}
