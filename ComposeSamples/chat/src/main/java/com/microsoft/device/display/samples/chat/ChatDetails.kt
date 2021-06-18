package com.microsoft.device.display.samples.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.chat.models.ContactModel
import com.microsoft.device.display.samples.chat.utils.ChatBubbleLeftArrowShape

@Composable
fun ChatDetails(
    models: List<ContactModel>,
    index: Int
) {
    val listState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        LazyColumn(
            state = listState
        ) {
            item {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.medium
                ) {
                    Text(
                        text = "16:00",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 5.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.padding(vertical = 12.dp))
            }
            item {
                Row(
                    modifier = Modifier.padding(start = 25.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = models[index].imageId),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier
                                .background(color = Color.Gray, shape = ChatBubbleLeftArrowShape())
                                .width(8.dp)
                        ) { }
                        Surface(
                            shape = RoundedCornerShape(4.dp, 4.dp, 4.dp, 4.dp),
                            color = Color.Gray
                        ) {
                            Text(
                                text = "Welcome to Surface Duo",
                                modifier = Modifier
                                    .padding(8.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
