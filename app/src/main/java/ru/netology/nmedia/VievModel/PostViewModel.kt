package ru.netology.nmedia.VievModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okio.IOException
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryHttp
import ru.netology.nmedia.util.SingleLiveEvent
import kotlin.concurrent.thread

private val empty = Post(
    id = 0,
    author = "",
    published = "",
    content = "",
    likedByMe = false,
    likes = 0,
//    shared = 0,
//    video = "",
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryHttp()


    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel> = _data

    //val post = repository.getAll()
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit> = _postCreated

    init {
        load()
    }

    fun save() {
        thread {
            edited.value?.let {
                repository.save(it)
                _postCreated.postValue(Unit)
            }
            edited.postValue(empty)
        }

//
//        edited.value?.let {
//            thread {
//                repository.save(it)
//                _postCreated.postValue(Unit)
//            }
//        }
//        edited.value = empty
    }

    fun load() {
        _data.postValue(FeedModel(loading = true))
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

    fun likeById(id: Long) {
        thread { repository.likeById(id) }
    }

    fun removeById(id: Long) {
        thread {
            val old = _data.value
            val updatePosts = _data.value?.posts.orEmpty().filter {
                it.id != id
            }

            _data.postValue(FeedModel(posts = updatePosts))
            try {
                repository.removeById(id)
            } catch (e: IOException) {
                _data.postValue(old)
            }
        }
    }

    fun saveContent(content: String) {
        thread {
            edited.value?.let {
                repository.save(it.copy(content = content))
            }
            edited.postValue(empty)
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun clearEdit() {
        edited.value = empty
    }

//    fun shareById(id: Long) = repository.increaseShare(id)

}