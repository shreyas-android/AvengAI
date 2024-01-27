package com.cogniheroid.framework.shared.core.chat

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.cogniheroid.framework.shared.core.chat.database.ChatDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(schema = ChatDatabase.Schema, context =  context, name = "android_chat.db")
    }
}