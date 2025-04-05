package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun increaseShare(id: Long)
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
    fun undo(post: Post)
}