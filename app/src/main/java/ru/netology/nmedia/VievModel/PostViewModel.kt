package ru.netology.nmedia.VievModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryHttp
import kotlin.concurrent.thread

private val empty = Post(
    id = 0,
    author = "",
    published = "",
    content = "",
    likedByMe = false,
    likeCounter = 0,
    shared = 0,
    )

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryHttp()

    init {
        load()
    }

    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel> = _data

    val post = repository.getAll()
    val edited = MutableLiveData(empty)

    fun load() {
        thread {
            try {
                val posts = repository.getAll()
                FeedModel(posts = posts, empty = posts.isEmpty())
            } catch (_: Exception) {
                FeedModel(error = true)

            }.also {
                _data.postValue(it)
            }


        }
    }

    fun likeById(id: Long) = repository.likeById(id)
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

    fun clearEdit() {
        edited.value = empty
    }

    fun shareById(id: Long) = repository.increaseShare(id)

}