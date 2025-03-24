package ru.netology.nmedia.repository

import android.app.DownloadManager
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.netology.nmedia.dto.Post
import java.util.concurrent.TimeUnit

class PostRepositoryHttp : PostRepository{

    private companion object {
        const val BASE_URL = "http://192.168.0.109:9999/"
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

    override fun getAll(): LiveData<List<Post>> {
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
        TODO("Not yet implemented")
    }

    override fun removeById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun save(post: Post) {
        TODO("Not yet implemented")
    }

    override fun undo(post: Post) {
        TODO("Not yet implemented")
    }
}