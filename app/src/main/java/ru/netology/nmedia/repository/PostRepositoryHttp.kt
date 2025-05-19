package ru.netology.nmedia.repository

import androidx.room.util.copyAndClose
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.internal.EMPTY_REQUEST
import ru.netology.nmedia.dto.Post
import java.io.IOException
import java.util.concurrent.TimeUnit

class PostRepositoryHttp : PostRepository {

    private companion object {
        const val BASE_URL = "http://10.0.2.2:9999/"
        val jsonType = "application/json".toMediaType()

    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()
    private val postsType = object : TypeToken<List<Post>>() {}.type


    override fun increaseShare(id: Long) {
        TODO("Not yet implemented")
    }



    override fun likeById(id: Long): Post {

        val request = Request.Builder()
            .url("${BASE_URL}api/slow/posts/$id/likes")
            .post(EMPTY_REQUEST)
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        val responseBody = requireNotNull(response.body) { "Body is null" }
        return gson.fromJson(responseBody.string(), Post::class.java)
    }
    fun likeByIdAsync(id: Long, onSuccess: (Post) -> Unit, onError: (Exception) -> Unit) {
        val request = Request.Builder()
            .url("${BASE_URL}api/slow/posts/$id/likes")
            .post(EMPTY_REQUEST)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        onError(IOException("Неуспешный ответ: ${it.code}"))
                        return
                    }
                    val responseBody = it.body?.string()
                    if (responseBody == null) {
                        onError(NullPointerException("Тело ответа пустое"))
                        return
                    }
                    try {
                        val post = gson.fromJson(responseBody, Post::class.java)
                        onSuccess(post)
                    } catch (e: Exception) {
                        onError(e)
                    }
                }
            }
        })
    }

    override fun unlikeById(id: Long): Post {

        val request = Request.Builder()
            .url("${BASE_URL}api/slow/posts/$id/likes")
            .delete()
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        val responseBody = requireNotNull(response.body) { "Body is null" }
        return gson.fromJson(responseBody.string(), Post::class.java)
    }

    fun unlikeByIdAsync(id: Long, onSuccess: (Post) -> Unit, onError: (Exception) -> Unit) {
        val request = Request.Builder()
            .url("${BASE_URL}api/slow/posts/$id/likes")
            .delete()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        onError(IOException("Неуспешный ответ: ${it.code}"))
                        return
                    }
                    val responseBody = it.body?.string()
                    if (responseBody == null) {
                        onError(NullPointerException("Тело ответа пустое"))
                        return
                    }
                    try {
                        val post = gson.fromJson(responseBody, Post::class.java)
                        onSuccess(post)
                    } catch (e: Exception) {
                        onError(e)
                    }
                }
            }
        })
    }

    override fun removeById(id: Long) {
        val request: Request = Request.Builder()
            .delete()
            .url("${BASE_URL}api/slow/posts/$id")
            .build()

        client.newCall(request)
            .execute()
            .close()
    }

    override fun removeByIdAsync(callback: PostRepository.RemoveByIdCallback, id: Long) {
        val request: Request = Request.Builder()
            .delete()
            .url("${BASE_URL}api/slow/posts/$id")
            .build()

        client.newCall(request)
            .enqueue(object : Callback{
                override fun onResponse(call: Call, response: Response) {
                    try {

                    } catch (e: Exception) {
                        callback.onError(e)
                    }

                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }
            })
    }

    override fun save(post: Post): Post {
        val request = Request.Builder()
            .post(gson.toJson(post, Post::class.java).toRequestBody(jsonType))
            .url("${BASE_URL}api/slow/posts")
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        val responseBody = requireNotNull(response.body) { "Body is null" }
        return gson.fromJson(responseBody.string(), Post::class.java)
    }

    override fun saveAsync(callback: PostRepository.SaveCallback, post: Post) {
        val requestBody = gson.toJson(post, Post::class.java).toRequestBody(jsonType)
        val request = Request.Builder()
            .post(requestBody)
            .url("${BASE_URL}api/slow/posts")
            .build()

        client.newCall(request)
            .enqueue(object: Callback {
                override fun onResponse(call: Call, response: Response) {
                    try {
                        val responseBody = response.body?.string() ?: throw RuntimeException("Body is null")
                        val savedPost = gson.fromJson(responseBody, Post::class.java)
                        callback.onSuccess(savedPost)
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }
            })
    }




    override fun undo(post: Post) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Post> {
        val request = Request.Builder()
            .get()
            .url("${BASE_URL}api/slow/posts")
            .build()
        val call = client.newCall(request)
        val response = call.execute()
        val responseBody = requireNotNull(response.body) { "Body is null" }
        return gson.fromJson(responseBody.string(), postsType)
    }

    override fun getAllAsync(callback: PostRepository.GetAllCallback) {
        val request = Request.Builder()
            .get()
            .url("${BASE_URL}api/slow/posts")
            .build()

        client.newCall(request)
            .enqueue(object: Callback{
                override fun onResponse(call: Call, response: Response) {
                    try {
                        val posts = response.body?.string() ?: throw RuntimeException("Body is null")
                        callback.onSuccess(gson.fromJson(posts, postsType))
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }

            })
    }


}