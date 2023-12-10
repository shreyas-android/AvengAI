package com.androidai.galaxy.ad

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val count = mutableStateOf(0)
}