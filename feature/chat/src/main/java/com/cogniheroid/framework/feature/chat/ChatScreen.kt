package com.cogniheroid.framework.feature.chat

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cogniheroid.framework.feature.chat.callback.ChatExternalCallback
import com.cogniheroid.framework.feature.chat.ui.conversationowner.ChatListScreen
import com.cogniheroid.framework.feature.chat.ui.conversationowner.ChatViewModel
import com.cogniheroid.framework.feature.chat.ui.conversations.ChatDetailScreen
import com.sparrow.framework.ui.theme.ComposeUITheme

enum class ChatRoute(val route: String) {
    CHAT_DETAIL("chat_detail"),
    HOME("home")
}

@Composable
fun ChatScreen(chatExternalCallback: ChatExternalCallback) {
    val chatViewModel = viewModel<ChatViewModel>(factory = Instance.conversationViewModelFactory)
    ComposeUITheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = ChatRoute.HOME.route) {

            composable(ChatRoute.HOME.route) {
               ChatListScreen(navController, chatViewModel)
            }

            composable(ChatRoute.CHAT_DETAIL.route) {
               chatViewModel.getSelectedChatListItem()?.let {
                    ChatDetailScreen(chatListItem = it, chatExternalCallback = chatExternalCallback){
                        navController.navigateUp()
                    }
                }
            }


        }
    }

}