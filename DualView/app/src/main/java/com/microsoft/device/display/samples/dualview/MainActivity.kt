package com.microsoft.device.display.samples.dualview

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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
        windowManager = WindowManager(this)
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)

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
}
