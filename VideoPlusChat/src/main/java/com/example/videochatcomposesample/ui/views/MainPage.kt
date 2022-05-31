package com.example.videochatcomposesample.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import com.example.videochatcomposesample.models.InfoProvider
import com.google.android.exoplayer2.ExoPlayer
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout
import com.microsoft.device.dualscreen.windowstate.WindowState

var infoProvider : InfoProvider = InfoProvider()

@Composable
fun MainPage(windowState: WindowState, player: ExoPlayer) {



    // very strange bug with Exoplayer and Windowstate so I have to define them here or else the player will not show
    // Most likely due to the fact that it does not recompose this main page
    var dual = (windowState.isDualScreen())
    var horizontal = windowState.isSingleLandscape()

    val focusManager = LocalFocusManager.current
    TwoPaneLayout(
        paneMode = infoProvider.paneMode,
        pane1 = {
                if (infoProvider.isFullScreen){
                    VideoPage(player = player)
                }
                else {
                    if (dual) {
                       VideoPage(player = player)
                    } else {
                        if (horizontal) {
                            RowView(focusManager = focusManager, player = player )
                        } else {
                            ColumnView(focusManager = focusManager, player = player)
                        }
                    }
                }
                },
        pane2 = {
            ChatPage(focusManager = focusManager)
        }


    )
}

// Focus shift example

//@Composable
//fun TF(focusManager: FocusManager, ourPane: FocusRequester, otherPane: FocusRequester){
//    var text by remember { mutableStateOf("") }
//    TextField(value = text, onValueChange = {text= it },  keyboardOptions = KeyboardOptions(
//        imeAction = ImeAction.Done,
//    ),
//        keyboardActions = KeyboardActions(
//            onAny = { focusManager.moveFocus(FocusDirection.Next) }
//        ),
//        modifier = Modifier.focusOrder(ourPane) { next = otherPane }
//    )
//}


@Composable
fun ColumnView(focusManager: FocusManager, player: ExoPlayer){
    Column(){
        Box(modifier = Modifier.fillMaxHeight(0.45f)){
            VideoPage(player = player)
        }

        Box(modifier = Modifier.fillMaxHeight()){
            ChatPage(focusManager = focusManager)
        }
    }
}

@Composable
fun RowView(focusManager: FocusManager, player: ExoPlayer){
    Row(){
        Box(modifier = Modifier.fillMaxWidth(0.65f)){
            VideoPage(player = player)
        }

        Box(modifier = Modifier.fillMaxWidth()){
            ChatPage(focusManager = focusManager)
        }
    }
}



    
