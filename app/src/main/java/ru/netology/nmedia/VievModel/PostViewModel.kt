package ru.netology.nmedia.VievModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepositoryInMemory

class PostViewModel : ViewModel() {
    private val repository = PostRepositoryInMemory()
    val post = repository.getPost()

    fun like() = repository.like()
    fun increaseShare() = repository.increaseShare()

}