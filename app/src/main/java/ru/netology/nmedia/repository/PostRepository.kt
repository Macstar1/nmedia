package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun increaseShare(id: Long)
    fun getAll(): List<Post>
    fun likeById(id: Long) : Post
    fun unlikeById(id: Long) : Post
    fun removeById(id: Long)
    fun save(post: Post): Post
    fun undo(post: Post)

    fun getAllAsync(callback: GetAllCallback)
    fun saveAsync(callback: SaveCallback, post: Post)
    fun removeByIdAsync(callback: RemoveByIdCallback, id: Long)

    interface RemoveByIdCallback {
        fun onSuccess(id: Long)
        fun onError(e: Exception)
    }

    interface SaveCallback {
        fun onSuccess(posts: Post)
        fun onError(e: Exception)
    }

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>)
        fun onError(e: Exception)
    }
}