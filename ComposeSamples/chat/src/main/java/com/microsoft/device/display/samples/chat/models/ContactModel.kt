package com.microsoft.device.display.samples.chat.models

data class ContactModel(
    val name: String,
    val imageId: Int = 0,
    val message: ArrayList<String>
)
