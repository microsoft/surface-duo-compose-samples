package com.example.navigationrail.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppStateViewModel : ViewModel() {
    val imageSelectionLiveData = MutableLiveData<Image>() // observe the image selection change
}
