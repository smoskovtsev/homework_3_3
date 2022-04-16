package ru.netology

import ru.netology.service.ChatService
import ru.netology.data.Message

fun main() {

    val chatService = ChatService()

    val message1 = Message(1,2,"Hello!")
    val message2 = Message(2,1,"Hi there!")
    val message3 = Message(1,2,"How are you?")
    val message4 = Message(2,1,"I'm fine!")

    chatService.createMessage(message1)
    chatService.createMessage(message2)
    chatService.createMessage(message3)
    chatService.createMessage(message4)

    chatService.getUnreadChatsCount(1)
    chatService.getChats(2)
    chatService.getMessages(1,1,1)
    chatService.deleteMessage(2,1,1)
    chatService.deleteChat(1,2)
}