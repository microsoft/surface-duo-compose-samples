package com.microsoft.device.display.samples.chat.models

import com.microsoft.device.display.samples.chat.R
import java.util.ArrayList

object DataProvider {
    val contactModels: ArrayList<ContactModel>
        get() {
            val items = ArrayList<ContactModel>()
            items.add(ContactModel(name = "Microsoft", imageId = R.drawable.microsoft))
            items.add(ContactModel(name = "Twitter", imageId = R.drawable.twitter))
            items.add(ContactModel(name = "Google", imageId = R.drawable.google))
            return items
        }
}
