package ru.netology.service

import org.junit.Test
import org.junit.Assert.*
import ru.netology.data.Chat
import ru.netology.data.Message

class ChatServiceTest {

    @Test
    fun getUnreadChatsCountTest() {
        val chatService = ChatService()
        val message1 = Message(1,2,"User 2 chat")
        chatService.createMessage(message1)
        assertEquals(chatService.getUnreadChatsCount(2), 1)
    }

    @Test
    fun getChatsTest() {
        val chatService = ChatService()
        val message1 = Message(1,2,"User 1 chat")
        chatService.createMessage(message1)
        assertTrue(chatService.getChats(1).isNotEmpty())
    }

    @Test
    fun getMessagesTest() {
        val chatService = ChatService()
        val message1 = Message(1, 2, "Hello!")
        val message2 = Message(1, 2, "It's me")
        chatService.createMessage(message1)
        chatService.createMessage(message2)
        assertTrue(chatService.getMessages(1, 1, 1).isNotEmpty())
    }

    @Test
    fun deleteMessageTest() {
        val chatService = ChatService()
        val message1 = Message(1,2,"First message")
        chatService.createMessage(message1)
        assertTrue(chatService.deleteMessage(1,2,1))
    }

    @Test
    fun deleteChatTest() {
        val chatService = ChatService()
        val message1 = Message(1,2,"First message")
        val message2 = Message(1,2,"Second message")
        chatService.createMessage(message1)
        chatService.createMessage(message2)
        assertTrue(chatService.deleteChat(1,2))
    }
}