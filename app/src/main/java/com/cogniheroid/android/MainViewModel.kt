package com.cogniheroid.android

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val count = mutableStateOf(0)
}