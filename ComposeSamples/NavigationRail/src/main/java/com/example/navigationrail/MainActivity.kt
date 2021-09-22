package com.example.navigationrail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModelProvider
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import com.example.navigationrail.models.AppStateViewModel
import com.example.navigationrail.ui.theme.ComposeSamplesTheme
import com.example.navigationrail.ui.view.SetupUI

@ExperimentalFoundationApi
class MainActivity : AppCompatActivity() {
    private lateinit var windowInfoRep: WindowInfoRepository
    private lateinit var appStateViewModel: AppStateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowInfoRep = windowInfoRepository()
        appStateViewModel = ViewModelProvider(this).get(AppStateViewModel::class.java)

        setContent {
            ComposeSamplesTheme {
                SetupUI(windowInfoRep, window, appStateViewModel)
            }
        }
    }
}
