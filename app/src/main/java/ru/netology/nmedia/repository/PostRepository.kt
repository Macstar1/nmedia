package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun increaseShare(id: Long)
    fun undo(post: Post)

    fun getAllAsync(callback: GetAllCallback)
    fun saveAsync(callback: SaveCallback, post: Post)
    fun removeByIdAsync(callback: RemoveByIdCallback, id: Long)
    fun unlikeByIdAsync(callback: LikeByIdCallback, id: Long)
    fun likeByIdAsync(callback: LikeByIdCallback, id: Long)

    interface LikeByIdCallback {
        fun onSuccess(post: Post)
        fun onError(e: Exception)
    }

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