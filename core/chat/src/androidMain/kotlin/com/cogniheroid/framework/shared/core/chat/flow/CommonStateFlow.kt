package com.cogniheroid.framework.shared.core.chat.flow

import kotlinx.coroutines.flow.StateFlow

actual class CommonStateFlow<T> actual constructor(
        private val flow: StateFlow<T>
) : StateFlow<T> by flow