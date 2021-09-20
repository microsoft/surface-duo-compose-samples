package com.example.navigationrail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import com.example.navigationrail.ui.theme.ComposeSamplesTheme
import com.example.navigationrail.ui.view.SetupUI

class MainActivity : AppCompatActivity() {
    private lateinit var windowInfoRep: WindowInfoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowInfoRep = windowInfoRepository()

        setContent {
            ComposeSamplesTheme {
                SetupUI(windowInfoRep, window)
            }
        }
    }
}
