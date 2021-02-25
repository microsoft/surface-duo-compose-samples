package com.microsoft.device.display.samples.companionpane.uicomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.companionpane.R

@Composable
fun EffectPanel() {
    Column(modifier = Modifier.fillMaxWidth()) {
        val imageWidth = 80.dp
        val controlWidth = 100.dp
        LeftAlignText(title = "Filters")
        Spacer(Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ImageWithText(R.drawable.gingham, "Gingham", imageWidth, controlWidth)
            ImageWithText(R.drawable.orignal, "Original", imageWidth, controlWidth)
            ImageWithText(R.drawable.lark, "Lark", imageWidth, controlWidth)
            ImageWithText(R.drawable.juno, "Juno", imageWidth, controlWidth)
            ImageWithText(R.drawable.ludwig, "Ludwig", imageWidth, controlWidth)
        }
    }
}

@Composable
fun ShortFilterControl() {
    Column(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ShortIconsPanel()
        }
        AdjustScale()
    }
}

@Composable
fun FullFilterControl() {
    Column(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        FullIconsPanel()
        AdjustScale()
    }
}

@Composable
fun AdjustScale() {
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.dot),
            modifier = Modifier.fillMaxWidth().height(5.dp),
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            contentDescription = null
        )
        Image(
            painter = painterResource(R.drawable.scale_icon),
            modifier = Modifier.fillMaxWidth().height(25.dp),
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            contentDescription = null
        )
    }
}

@Composable
fun FullIconsPanel() {
    Row(
        modifier = Modifier.fillMaxWidth().height(25.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.filter_icon),
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.hdr_icon),
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.ecllipse_icon),
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.vertical_icon),
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.horizontal_icon),
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.zoom_icon),
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.brightness_icon),
            contentDescription = null
        )
    }
}

@Composable
fun ShortIconsPanel() {
    Row(
        modifier = Modifier.width(200.dp).height(25.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.ecllipse_icon),
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.vertical_icon),
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.horizontal_icon),
            contentDescription = null
        )
    }
}
