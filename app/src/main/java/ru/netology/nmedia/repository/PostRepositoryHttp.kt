package ru.netology.nmedia.repository

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import java.io.IOException
import java.util.concurrent.TimeUnit

class PostRepositoryHttp : PostRepository {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun <T> executeRequest(request: Request, parse: (Response) -> T): T {
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            when (response.code) {
                in 400..499 -> throw RuntimeException("Клиентская ошибка: ${response.code} - ${response.message}")
                in 500..599 -> throw RuntimeException("Ошибка сервера: ${response.code} - ${response.message}")
                else -> throw RuntimeException("Неизвестная ошибка: ${response.code} - ${response.message}")
            }
        }
        response.use {
            return parse(it)
        }
    }

    override fun increaseShare(id: Long) {
        TODO("Not yet implemented")
    }

    override fun likeByIdAsync(callback: PostRepository.LikeByIdCallback, id: Long) {
        PostsApi.retrofitService.likeById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(call: retrofit2.Call<Post>, response: retrofit2.Response<Post>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    if (post != null) {
                        callback.onSuccess(post)
                    } else {
                        callback.onError(NullPointerException("Тело ответа пустое"))
                    }
                } else {
                    callback.onError(IOException("Неуспешный ответ: ${response.code()}"))
                }
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(Exception(t))
            }
        })
    }

    override fun unlikeByIdAsync(callback: PostRepository.LikeByIdCallback, id: Long) {
        PostsApi.retrofitService.unlikeById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(call: retrofit2.Call<Post>, response: retrofit2.Response<Post>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    if (post != null) {
                        callback.onSuccess(post)
                    } else {
                        callback.onError(NullPointerException("Пустое тело ответа"))
                    }
                } else {
                    callback.onError(IOException("Неуспешный ответ: ${response.code()}"))
                }
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(Exception(t))
            }
        })
    }

    override fun removeByIdAsync(callback: PostRepository.RemoveByIdCallback, id: Long) {
        PostsApi.retrofitService.removeById(id).enqueue(object : retrofit2.Callback<Unit> {
            override fun onResponse(call: retrofit2.Call<Unit>, response: retrofit2.Response<Unit>) {
                if (response.isSuccessful) {
                    callback.onSuccess(id)
                } else {
                    callback.onError(RuntimeException("Ошибка: ${response.code()}"))
                }
            }

            override fun onFailure(call: retrofit2.Call<Unit>, t: Throwable) {
                callback.onError(Exception(t))
            }
        })
    }

    override fun saveAsync(callback: PostRepository.SaveCallback, post: Post) {
        PostsApi.retrofitService.save(post).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(call: retrofit2.Call<Post>, response: retrofit2.Response<Post>) {
                if (response.isSuccessful) {
                    val savedPost = response.body()
                    if (savedPost != null) {
                        callback.onSuccess(savedPost)
                    } else {
                        callback.onError(RuntimeException("Ответ не содержит поста"))
                    }
                } else {
                    callback.onError(RuntimeException("Код ответа: ${response.code()}"))
                }
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(Exception(t))
            }
        })
    }

    override fun undo(post: Post) {
        TODO("Not yet implemented")
    }

    override fun getAllAsync(callback: PostRepository.GetAllCallback) {
        PostsApi.retrofitService.getAll().enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onResponse(call: retrofit2.Call<List<Post>>, response: retrofit2.Response<List<Post>>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                    return
                } else {
                    callback.onError(RuntimeException("${response.code()}: ${response.message()}"))
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Post>>, t: Throwable) {
                callback.onError(Exception(t))
            }
        })
    }
}