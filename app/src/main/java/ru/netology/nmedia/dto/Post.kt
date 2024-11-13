package ru.netology.nmedia.dto

class Post(
    val id: Long = 0,
    val content: String = "",
    val published: String = "",
    val author: String = "",
    var likedByMe: Boolean = false,
)