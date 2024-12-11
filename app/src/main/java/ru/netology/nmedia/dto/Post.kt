package ru.netology.nmedia.dto

data class Post(
    val id: Long = 0,
    val content: String = "",
    val published: String = "",
    val author: String = "",
    var likedByMe: Boolean = false,
    val likeCounter: Int = 0,
    val shared: Int = 0,
    val video: String = "",
)