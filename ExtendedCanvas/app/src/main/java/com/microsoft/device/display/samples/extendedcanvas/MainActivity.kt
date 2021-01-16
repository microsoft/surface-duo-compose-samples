package com.microsoft.device.display.samples.extendedcanvas

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.microsoft.device.display.samples.extendedcanvas.ui.ExtendedCanvasAppsTheme
import com.microsoft.device.display.samples.extendedcanvas.viewmodel.AppStateViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var appStateViewModel: AppStateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)
        val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        appStateViewModel.setIsScreenPortraitLiveData(isPortrait)

        super.onCreate(savedInstanceState)

        setContent {
            ExtendedCanvasAppsTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                BasicText(
                                    text = stringResource(R.string.app_name),
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }
                        )
                    },
                    bodyContent = { MainPage(appStateViewModel) }
                )
            }
        }
    }

    // Checks the orientation of the screen
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val isPortrait = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT
        appStateViewModel.setIsScreenPortraitLiveData(isPortrait)
    }
}
