package database

import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String

public data class GetJoinedMessages(
  public val messageId: Long,
  public val chatId: Long,
  public val senderId: Long,
  public val message: String?,
  public val messageStartDate: String,
  public val messageTime: Long,
  public val fileUri: String?,
  public val readStatus: Int?,
  public val messageType: Int?,
  public val messageContentType: Int?,
  public val replyMessageId: Long?,
  public val sId: Long?,
  public val senderName: String?,
  public val senderImageUri: String?,
  public val isUser: Boolean?,
)
