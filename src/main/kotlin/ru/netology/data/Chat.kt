package ru.netology.data

data class Chat(
    var id: Int,
    val senderId: Int,
    val recipientId: Int,
    val messages: MutableList<Message> = mutableListOf()
)
