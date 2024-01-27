package com.cogniheroid.framework.shared.core.chat

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.cogniheroid.framework.shared.core.chat.database.ChatDatabase

actual class DatabaseDriverFactory {
  actual fun createDriver(): SqlDriver {
    return NativeSqliteDriver(ChatDatabase.Schema, "ios_chat.db")
  }
}