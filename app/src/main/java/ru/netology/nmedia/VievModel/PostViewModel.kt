package ru.netology.nmedia.VievModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepositoryInMemory

private val empty = Post()
class PostViewModel : ViewModel() {
    private val repository = PostRepositoryInMemory()
    val post = repository.getAll()
    val edited = MutableLiveData(empty)

    fun likeById(id: Long) = repository.likeById(id)
    fun increaseShare(id: Long) = repository.increaseShare(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun saveContent(content: String) {
        edited.value?.let {
            repository.save(it.copy(content = content))
        }
        edited.value = empty

    }

    fun edit(post: Post) {
        edited.value = post
    }

}