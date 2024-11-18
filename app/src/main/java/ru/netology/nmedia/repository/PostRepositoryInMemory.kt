package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory : PostRepository {

    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий.",
        published = "Сегодня",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, дизайну, аналитике и управлению. Мы растём сами и помогаем студентам: от новичков до уверенных профессианалов. Но самое важноеостаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиься точнее, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
        likedByMe = false,
        likeCounter = 992,
        shared = 992,
    )

    private val data = MutableLiveData(post)

    override fun getPost(): LiveData<Post> {
        return data
    }

    override fun like() {
        post = post.copy(
            likedByMe = !post.likedByMe,
            likeCounter = if (!post.likedByMe) {
                post.likeCounter + 1
            } else {
                post.likeCounter - 1
            },
        )
        data.value = post
    }

    override fun increaseShare() {
        val currentPost = data.value ?: return
        val updatePost = currentPost.copy(shared = currentPost.shared + 1)
        data.value = updatePost
    }
}