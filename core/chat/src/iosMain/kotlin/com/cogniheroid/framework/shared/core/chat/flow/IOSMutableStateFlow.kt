package com.cogniheroid.framework.shared.core.chat.flow

import kotlinx.coroutines.flow.MutableStateFlow

class IOSMutableStateFlow<T>(
        initialValue: T
) : CommonMutableStateFlow<T>(MutableStateFlow(initialValue))