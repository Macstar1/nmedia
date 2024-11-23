package ru.netology.nmedia.VievModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepositoryInMemory

class PostViewModel : ViewModel() {
    private val repository = PostRepositoryInMemory()
    val post = repository.getAll()

    fun likeById(id: Long) = repository.likeById(id)
    fun increaseShare(id: Long) = repository.increaseShare(id)

}