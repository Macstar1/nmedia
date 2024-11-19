package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun like()
    fun increaseShare()
    fun getPost(): LiveData<Post>
}