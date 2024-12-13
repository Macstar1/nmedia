package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий.",
            published = "Сегодня",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, дизайну, аналитике и управлению. Мы растём сами и помогаем студентам: от новичков до уверенных профессианалов. Но самое важноеостаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиься точнее, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
            likedByMe = false,
            likeCounter = 992,
            shared = 992,
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий.",
            published = "Вчера",
            content = "Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
            likedByMe = false,
            likeCounter = 92,
            shared = 92,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий.",
            published = "Сегодня",
            content = "3. Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, дизайну, аналитике и управлению. Мы растём сами и помогаем студентам: от новичков до уверенных профессианалов. Но самое важноеостаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиься точнее, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
            likedByMe = false,
            likeCounter = 992,
            shared = 992,
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий.",
            published = "Вчера",
            content = "4. Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
            likedByMe = false,
            likeCounter = 92,
            shared = 92,
        )

    )

    private val data = MutableLiveData(posts)

    override fun increaseShare(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else {
                it.copy(shared = it.shared + 1)
            }
        }
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else {
                it.copy(
                    likedByMe = !it.likedByMe,
                    likeCounter = if (it.likedByMe) {
                        it.likeCounter - 1
                    } else {
                        it.likeCounter + 1
                    }
                )
            }
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(post.copy(id = nextId++, author = "Me", published = "Now")) + posts
        } else {
            posts.map {
                if (it.id != post.id) it else it.copy(content = post.content)
            }
        }
        data.value = posts
    }

    override fun undo(post: Post) {
        data.value = posts
    }

    override fun video() {

    }

}