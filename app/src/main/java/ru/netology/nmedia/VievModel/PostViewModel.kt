package ru.netology.nmedia.VievModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.util.copy
import okio.IOException
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryHttp
import ru.netology.nmedia.util.SingleLiveEvent

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

//    fun save() {
//        thread {
//            edited.value?.let {
//                repository.save(it)
//                _postCreated.postValue(Unit)
//            }
//            edited.postValue(empty)
//        }
//    }

    fun save() {
        edited.value?.let {
            repository.saveAsync(object : PostRepository.SaveCallback {
                override fun onSuccess(posts: Post) {
                    _postCreated.postValue(Unit)
                }

                override fun onError(e: Exception) {
                    edited.postValue(empty)
                }
            }, it)
        }
    }

//    fun load() {
//        _data.postValue(FeedModel(loading = true))
//        thread {
//            try {
//                val posts = repository.getAll()
//                FeedModel(posts = posts, empty = posts.isEmpty())
//            } catch (_: Exception) {
//                FeedModel(error = true)
//
//            }.also {
//                _data.postValue(it)
//            }
//        }
//    }

    fun load() {
        _data.postValue(FeedModel(loading = true))
        repository.getAllAsync(object : PostRepository.GetAllCallback {
            override fun onSuccess(posts: List<Post>) {
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    //    fun likeById(post: Post) {
//        thread {
//            try {
//                val updatedPost = if (post.likedByMe) {
//                    repository.unlikeById(post.id)
//                } else {
//                    repository.likeById(post.id)
//                }
//
//                val updatedPosts = _data.value?.posts?.map {
//                    if (it.id == updatedPost.id) {
//                        updatedPost
//                    } else {
//                        it
//                    }
//                }
//                _data.postValue(FeedModel(posts = updatedPosts.orEmpty()))
//            } catch (e: Exception) {
//                _data.postValue(FeedModel(error = true))
//            }
//        }
//    }

//
    fun likeById(id: Long) {
        val currentFeed = _data.value ?: return

        val updatedPosts = currentFeed.posts.map { post ->
            if (post.id == id) {
                if (post.likedByMe) {
                    repository.unlikeByIdAsync(object : PostRepository.LikeByIdCallback {
                        override fun onSuccess(post: Post) {
                            updatePostInFeed(post)
                        }
                        override fun onError(e: Exception) {
                            _data.postValue(FeedModel(error = true))
                        }
                    }, id)
                } else {
                    repository.likeByIdAsync(object : PostRepository.LikeByIdCallback {
                        override fun onSuccess(post: Post) {
                            updatePostInFeed(post)
                        }

                        override fun onError(e: Exception) {
                            _data.postValue(FeedModel(error = true))
                        }
                    }, id)
                }
                post.copy(likedByMe = !post.likedByMe)
            } else {
                post
            }
        }
        _data.value = currentFeed.copy(posts = updatedPosts)
    }

    private fun updatePostInFeed(updatedPost: Post) {
        val currentFeed = _data.value ?: return
        val newPosts = currentFeed.posts.map { post ->
            if (post.id == updatedPost.id) {
                updatedPost
            } else {
                post
            }
        }
        _data.postValue(currentFeed.copy(posts = newPosts))
    }


    //    fun removeById(id: Long) {
//        thread {
//            val old = _data.value
//            val updatePosts = _data.value?.posts.orEmpty().filter {
//                it.id != id
//            }
//
//            _data.postValue(FeedModel(posts = updatePosts))
//            try {
//                repository.removeById(id)
//            } catch (e: IOException) {
//                _data.postValue(old)
//            }
//        }
//    }

    fun removeById(id: Long) {
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

    fun saveContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun clearEdit() {
        edited.value = empty
    }

//    fun shareById(id: Long) = repository.increaseShare(id)

}