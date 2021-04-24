package com.microsoft.device.display.twopanelayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.microsoft.device.display.twopanelayout.sample.ui.theme.TwoPaneLayoutTheme

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
    TwoPaneLayout(modifier = Modifier.fillMaxSize()) {
        Column() {
            Text("One pane: Dual screens give you more opportunity to display content in several different patterns. Depending on the pattern you choose, the BLUE margins near the hinge are meant to be optional. For example, if you follow the List-Detail pattern, you might have to keep in mind the BLUE margins. However, if you follow the Extended Canvas pattern, then your content can flow across the hinge without having to worry about the BLUE margins near the hinge.")
            Text("Two pane.....")
        }
        Column() {
            Text("Three pane: Dual screens give you more opportunity to display content in several different patterns. Depending on the pattern you choose, the BLUE margins near the hinge are meant to be optional. For example, if you follow the List-Detail pattern, you might have to keep in mind the BLUE margins. However, if you follow the Extended Canvas pattern, then your content can flow across the hinge without having to worry about the BLUE margins near the hinge.")
            Text("Four pane.....")
        }
    }
}




