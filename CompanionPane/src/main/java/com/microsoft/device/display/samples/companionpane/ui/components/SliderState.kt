package com.microsoft.device.display.samples.companionpane.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

const val MAGIC_WAND_KEY = "magicWandKey"
const val DEFINITION_KEY = "definitionKey"
const val VIGNETTED_KEY = "vignettedKey"
const val BRIGHTNESS_KEY = "brightnessKey"

class SliderState(
    magicWand: Float,
    definition: Float,
    vignette: Float,
    brightness: Float
) {

    private var _magicWand by mutableStateOf(magicWand)
    var magicWand: Float
        get() = _magicWand
        set(value) {
            _magicWand = value
        }

    private var _definition by mutableStateOf(definition)
    var definition: Float
        get() = _definition
        set(value) {
            _definition = value
        }

    private var _vignette by mutableStateOf(vignette)
    var vignette: Float
        get() = _vignette
        set(value) {
            _vignette = value
        }

    private var _brightness by mutableStateOf(brightness)
    var brightness: Float
        get() = _brightness
        set(value) {
            _brightness = value
        }

    companion object {
        val Saver = run {
            mapSaver(
                save = {
                    mapOf(
                        MAGIC_WAND_KEY to it.magicWand,
                        DEFINITION_KEY to it.definition,
                        VIGNETTED_KEY to it.vignette,
                        BRIGHTNESS_KEY to it.brightness,
                    )
                },
                restore = {
                    SliderState(
                        it[MAGIC_WAND_KEY] as Float,
                        it[DEFINITION_KEY] as Float,
                        it[VIGNETTED_KEY] as Float,
                        it[BRIGHTNESS_KEY] as Float
                    )
                }
            )
        }
    }
}

@Composable
fun rememberSliderState(
    magicWand: Float = 50f,
    definition: Float = 50f,
    vignette: Float = 50f,
    brightness: Float = 50f,
): SliderState = rememberSaveable(saver = SliderState.Saver) {
    SliderState(
        magicWand = magicWand,
        definition = definition,
        vignette = vignette,
        brightness = brightness
    )
}
