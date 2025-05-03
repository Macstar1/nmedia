package ru.netology.nmedia.repository

import android.app.DownloadManager
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import ru.netology.nmedia.dto.Post
import java.util.concurrent.TimeUnit

class PostRepositoryHttp : PostRepository{

    private companion object {
        const val BASE_URL = "http://192.168.0.113:9999/"
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

    override fun likeById(id: Long) {
        val request = Request.Builder()
            .url("${BASE_URL}api/slow/posts/$id/likes")
            .build()

        client.newCall(request)
            .execute()
            .close()
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

    override fun undo(post: Post) {
        TODO("Not yet implemented")
    }
}