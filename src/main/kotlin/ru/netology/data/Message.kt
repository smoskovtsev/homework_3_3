package ru.netology.data

data class Message(
    var senderId: Int,
    var recipientId: Int,
    var messageText: String,
    var id: Int = 0,
    var isRead: Boolean = true,
    var isSent: Boolean = false
)