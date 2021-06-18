package com.microsoft.device.display.samples.chat.models

import com.microsoft.device.display.samples.chat.R
import java.util.ArrayList

object DataProvider {
    val contactModels: ArrayList<ContactModel>
        get() {
            val items = ArrayList<ContactModel>()
            items.add(ContactModel(name = "Microsoft", imageId = R.drawable.logo))
            return items
        }
}
