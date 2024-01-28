package database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import migrations.Messages

public class MessagesQueries(
  driver: SqlDriver,
  private val MessagesAdapter: Messages.Adapter,
) : TransacterImpl(driver) {
  public fun <T : Any> getLocalMessageId(mapper: (MAX: Long?) -> T): Query<T> = Query(-73_165_612,
      arrayOf("Messages"), driver, "messages.sq", "getLocalMessageId",
      "SELECT MAX(messageId) FROM Messages") { cursor ->
    mapper(
      cursor.getLong(0)
    )
  }

  public fun getLocalMessageId(): Query<GetLocalMessageId> = getLocalMessageId { MAX ->
    GetLocalMessageId(
      MAX
    )
  }

  public fun <T : Any> getMessages(chatId: Long, mapper: (
    messageId: Long,
    chatId: Long,
    senderId: Long,
    message: String?,
    messageStartDate: String,
    messageTime: Long,
    fileUri: String?,
    readStatus: Int?,
    messageType: Int?,
    messageContentType: Int?,
    replyMessageId: Long?,
  ) -> T): Query<T> = GetMessagesQuery(chatId) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getLong(1)!!,
      cursor.getLong(2)!!,
      cursor.getString(3),
      cursor.getString(4)!!,
      cursor.getLong(5)!!,
      cursor.getString(6),
      cursor.getLong(7)?.let { MessagesAdapter.readStatusAdapter.decode(it) },
      cursor.getLong(8)?.let { MessagesAdapter.messageTypeAdapter.decode(it) },
      cursor.getLong(9)?.let { MessagesAdapter.messageContentTypeAdapter.decode(it) },
      cursor.getLong(10)
    )
  }

  public fun getMessages(chatId: Long): Query<Messages> = getMessages(chatId) { messageId, chatId_,
      senderId, message, messageStartDate, messageTime, fileUri, readStatus, messageType,
      messageContentType, replyMessageId ->
    Messages(
      messageId,
      chatId_,
      senderId,
      message,
      messageStartDate,
      messageTime,
      fileUri,
      readStatus,
      messageType,
      messageContentType,
      replyMessageId
    )
  }

  public fun <T : Any> getJoinedMessages(chatId: Long, mapper: (
    messageId: Long,
    chatId: Long,
    senderId: Long,
    message: String?,
    messageStartDate: String,
    messageTime: Long,
    fileUri: String?,
    readStatus: Int?,
    messageType: Int?,
    messageContentType: Int?,
    replyMessageId: Long?,
    sId: Long?,
    senderName: String?,
    senderImageUri: String?,
    isUser: Boolean?,
  ) -> T): Query<T> = GetJoinedMessagesQuery(chatId) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getLong(1)!!,
      cursor.getLong(2)!!,
      cursor.getString(3),
      cursor.getString(4)!!,
      cursor.getLong(5)!!,
      cursor.getString(6),
      cursor.getLong(7)?.let { MessagesAdapter.readStatusAdapter.decode(it) },
      cursor.getLong(8)?.let { MessagesAdapter.messageTypeAdapter.decode(it) },
      cursor.getLong(9)?.let { MessagesAdapter.messageContentTypeAdapter.decode(it) },
      cursor.getLong(10),
      cursor.getLong(11),
      cursor.getString(12),
      cursor.getString(13),
      cursor.getBoolean(14)
    )
  }

  public fun getJoinedMessages(chatId: Long): Query<GetJoinedMessages> = getJoinedMessages(chatId) {
      messageId, chatId_, senderId, message, messageStartDate, messageTime, fileUri, readStatus,
      messageType, messageContentType, replyMessageId, sId, senderName, senderImageUri, isUser ->
    GetJoinedMessages(
      messageId,
      chatId_,
      senderId,
      message,
      messageStartDate,
      messageTime,
      fileUri,
      readStatus,
      messageType,
      messageContentType,
      replyMessageId,
      sId,
      senderName,
      senderImageUri,
      isUser
    )
  }

  public fun insertMessage(
    messageId: Long?,
    chatId: Long,
    senderId: Long,
    message: String?,
    messageStartDate: String,
    messageTime: Long,
    fileUri: String?,
    readStatus: Int?,
    messageType: Int?,
    messageContentType: Int?,
    replyMessageId: Long?,
  ) {
    driver.execute(-173_663_723, """
        |INSERT OR REPLACE INTO Messages (messageId, chatId, senderId, message, messageStartDate, messageTime, fileUri, readStatus, messageType, messageContentType, replyMessageId)
        |VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)
        """.trimMargin(), 11) {
          bindLong(0, messageId)
          bindLong(1, chatId)
          bindLong(2, senderId)
          bindString(3, message)
          bindString(4, messageStartDate)
          bindLong(5, messageTime)
          bindString(6, fileUri)
          bindLong(7, readStatus?.let { MessagesAdapter.readStatusAdapter.encode(it) })
          bindLong(8, messageType?.let { MessagesAdapter.messageTypeAdapter.encode(it) })
          bindLong(9, messageContentType?.let { MessagesAdapter.messageContentTypeAdapter.encode(it)
              })
          bindLong(10, replyMessageId)
        }
    notifyQueries(-173_663_723) { emit ->
      emit("Messages")
    }
  }

  public fun updateMessage(
    chatId: Long,
    senderId: Long,
    message: String?,
    messageStartDate: String,
    messageTime: Long,
    fileUri: String?,
    readStatus: Int?,
    messageType: Int?,
    messageContentType: Int?,
    replyMessageId: Long?,
    messageId: Long,
  ) {
    driver.execute(-1_158_106_619, """
        |UPDATE Messages
        |SET
        |  chatId = ?,
        |  senderId = ?,
        |  message = ?,
        |  messageStartDate = ?,
        |  messageTime = ?,
        |  fileUri = ?,
        |  readStatus = ?,
        |  messageType = ?,
        |  messageContentType = ?,
        |  replyMessageId = ?
        |WHERE
        |  messageId = ?
        """.trimMargin(), 11) {
          bindLong(0, chatId)
          bindLong(1, senderId)
          bindString(2, message)
          bindString(3, messageStartDate)
          bindLong(4, messageTime)
          bindString(5, fileUri)
          bindLong(6, readStatus?.let { MessagesAdapter.readStatusAdapter.encode(it) })
          bindLong(7, messageType?.let { MessagesAdapter.messageTypeAdapter.encode(it) })
          bindLong(8, messageContentType?.let { MessagesAdapter.messageContentTypeAdapter.encode(it)
              })
          bindLong(9, replyMessageId)
          bindLong(10, messageId)
        }
    notifyQueries(-1_158_106_619) { emit ->
      emit("Messages")
    }
  }

  public fun deleteMessage(messageId: Long) {
    driver.execute(133_071_587, """DELETE FROM Messages WHERE messageId = ?""", 1) {
          bindLong(0, messageId)
        }
    notifyQueries(133_071_587) { emit ->
      emit("Messages")
    }
  }

  public fun deleteMessageByChatId(chatId: Long) {
    driver.execute(-1_273_588_275, """DELETE FROM Messages WHERE chatId=?""", 1) {
          bindLong(0, chatId)
        }
    notifyQueries(-1_273_588_275) { emit ->
      emit("Messages")
    }
  }

  private inner class GetMessagesQuery<out T : Any>(
    public val chatId: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("Messages", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("Messages", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-1_629_525_111, """SELECT * FROM Messages WHERE chatId = ?""", mapper,
        1) {
      bindLong(0, chatId)
    }

    override fun toString(): String = "messages.sq:getMessages"
  }

  private inner class GetJoinedMessagesQuery<out T : Any>(
    public val chatId: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("Messages", "Sender", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("Messages", "Sender", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-115_712_974,
        """SELECT Messages.*, Sender.senderId AS sId, senderName, senderImageUri, isUser FROM Messages LEFT JOIN Sender ON Messages.senderId = Sender.senderId WHERE chatId = ?""",
        mapper, 1) {
      bindLong(0, chatId)
    }

    override fun toString(): String = "messages.sq:getJoinedMessages"
  }
}
