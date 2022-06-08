package com.microsoft.device.display.samples.calculator.ui.pages.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.calculator.R
import com.microsoft.device.display.samples.calculator.models.Action
import com.microsoft.device.display.samples.calculator.models.Equation
import com.microsoft.device.display.samples.calculator.models.actionStringEquivalent
import com.microsoft.device.display.samples.calculator.models.equationStringEquivalent
import com.microsoft.device.display.samples.calculator.ui.pages.calculatorModel

@Composable
fun EquationButton(eq: Equation, color: Color = MaterialTheme.colors.primary) {
    ButtonLayout(
        color = color,
        content = equationStringEquivalent(eq)
    ) { calculatorModel.updateEquation(eq) }
}

@Composable
fun ActionButton(ac: Action, color: Color = MaterialTheme.colors.primary) {
    ButtonLayout(
        color = color,
        content = actionStringEquivalent(ac),
        useIcon = ac == Action.DEL,
    ) { calculatorModel.assertAction(ac) }
}

@Composable
fun NumericButton(num: String, color: Color = MaterialTheme.colors.primary) {
    ButtonLayout(
        color = color,
        content = num
    ) { calculatorModel.addValues(num) }
}

@Composable
fun ButtonLayout(
    color: Color,
    content: String,
    useIcon: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .padding(5.dp)
            .size(90.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        shape = MaterialTheme.shapes.large
    ) {
        if (useIcon) {
            ButtonIcon()
        } else {
            ButtonText(content = content)
        }
    }
}

@Composable
fun ButtonText(content: String) {
    val button = MaterialTheme.typography.button
    var textStyle by remember { mutableStateOf(button) }
    var readyToDraw by remember { mutableStateOf(false) }
    Text(
        text = content,
        style = textStyle,
        textAlign = TextAlign.Center,
        maxLines = 1,
        softWrap = false,
        // for text resizing
        modifier = Modifier.drawWithContent {
            if (readyToDraw) drawContent()
        },
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.didOverflowWidth) {
                textStyle = textStyle.copy(fontSize = textStyle.fontSize * 0.9)
            } else {
                readyToDraw = true
            }
        }
    )
}

@Composable
fun ButtonIcon() {
    // so far only delete needs an icon, but we could add more
    Icon(
        painter = painterResource(id = R.drawable.backspace),
        contentDescription = stringResource(id = R.string.delete),
        tint = MaterialTheme.colors.onBackground,
        modifier = Modifier.fillMaxSize(0.9f)
    )
}
