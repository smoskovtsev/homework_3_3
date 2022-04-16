package ru.netology.service

import ru.netology.data.Chat
import ru.netology.data.Message

class ChatService {
    private var chats = mutableListOf<Chat>()

    fun getUnreadChatsCount(userId: Int): Int {
        return chats.filter { it.senderId == userId }.count { list ->
            list.messages.any { it.isSent && !it.isRead }
        }
    }

    fun getChats(userId: Int): List<Chat> {
        chats.filter { it.senderId == userId }.filter { it.messages.isEmpty() }.forEach { println("Пустой чат") }
        return chats.filter { it.senderId == userId }.filter { it.messages.isNotEmpty() }
    }

    fun getMessages(chatId: Int, lastMessageId: Int, count: Int): List<Message> {
        val list = mutableListOf<Message>()
        chats.find { it.id == chatId }?.let { chat ->
            chat.messages.findLast { lastMessage -> lastMessage.id == lastMessageId }?.let {
                if (chats[chatId - 1].messages.subList(lastMessageId, chats[chatId].messages.size).size <= count) {
                    list.addAll(chats[chatId - 1].messages.subList(lastMessageId, chats[chatId - 1].messages.size))
                    chats[chatId - 1].messages.filter { message -> !message.isRead }
                        .forEach { element -> element.isRead = true }
                } else {
                    list.addAll(chats[chatId - 1].messages.subList(lastMessageId, lastMessageId + count))
                    chats[chatId - 1].messages.filter { message -> !message.isRead }
                        .forEach { element -> element.isRead = true }
                }
            }
        }
        return list
    }

    fun createMessage(message: Message) {
        // Создание чата
        if(chats.none { it.senderId == message.senderId && it.recipientId == message.recipientId }) {
            val chatId = if(chats.isEmpty()) 1 else chats.last().id + 1
            chats.add(Chat(chatId, message.senderId, message.recipientId).also {
                it.messages.run {
                    this.add(message)
                    this.last().id += 1
                }
            })
            // Создание чата
            if (chats.none { it.senderId == message.recipientId && it.recipientId == message.senderId }) {
                chats.add(Chat(chatId + 1, message.recipientId, message.senderId).also { it.messages.add(message.copy(isRead = false, isSent = true)) })
            }
        }
        // Отправка сообщения в созданном чате
        else {
            chats.find { it.senderId == message.senderId && it.recipientId == message.recipientId }?.let {
                val lastMessage = chats.last { it.senderId == message.recipientId && it.recipientId == message.senderId }.messages.last()
                it.messages.add(message.copy(id = lastMessage.id + 1))
            }
            chats.find { it.senderId == message.recipientId && it.recipientId == message.senderId }?.let {
                val lastMessage = chats.last { it.senderId == message.recipientId && it.recipientId == message.senderId }.messages.last()
                it.messages.add(message.copy(id = lastMessage.id + 1, isRead = false, isSent = true))
            }

        }
    }

    fun deleteMessage(userId: Int, anotherUserId: Int, messageId: Int) : Boolean {
        return if(chats.any { it.senderId == userId && it.recipientId == anotherUserId }) {
            chats.filter { it.senderId == userId && it.recipientId == anotherUserId }
                .find { it.messages.size > 1 }?.let {
                    chats.filter { it.senderId == userId && it.recipientId == anotherUserId }
                        .forEach { it.messages.removeIf { it.id == messageId } }
                } ?: deleteChat(userId, anotherUserId)
            true
        } else false
    }

    fun deleteChat(userId: Int, anotherUserId: Int) : Boolean {
        return if(chats.any { it.senderId == userId && it.recipientId == anotherUserId }) {
            chats.removeIf { it.senderId == userId && it.recipientId == anotherUserId }
            true
        } else false
    }
}
