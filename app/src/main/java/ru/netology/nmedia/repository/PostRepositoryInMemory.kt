package ru.netology.nmedia.repository

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
            likes = 992,
//            shared = 992,
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий.",
            published = "Вчера",
            content = "Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
            likedByMe = false,
            likes = 92,
//            shared = 92,
//            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий.",
            published = "Сегодня",
            content = "3. Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, дизайну, аналитике и управлению. Мы растём сами и помогаем студентам: от новичков до уверенных профессианалов. Но самое важноеостаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиься точнее, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
            likedByMe = false,
            likes = 992,
//            shared = 992,
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий.",
            published = "Вчера",
            content = "4. Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
            likedByMe = false,
            likes = 92,
//            shared = 92,
        )

    )

    private val data = MutableLiveData(posts)

    override fun increaseShare(id: Long) {
//        posts = posts.map {
//            if (it.id != id) it
//            else {
//                it.copy(shared = it.shared + 1)
//            }
//        }
//        data.value = posts
    }

    override fun getAll(): List<Post> = data as List<Post>

    override fun likeById(id: Long): Post {
//        posts = posts.map {
//            if (it.id != id) it
//            else {
//                it.copy(
//                    likedByMe = !it.likedByMe,
//                    likes = if (it.likedByMe) {
//                        it.likes - 1
//                    } else {
//                        it.likes + 1
//                    }
//                )
//            }
//        }
//        data.value = posts
        return TODO("Provide the return value")
    }

    override fun unlikeById(id: Long): Post {
        TODO("Not yet implemented")
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post): Post {
        posts = if (post.id == 0L) {
            listOf(post.copy(id = nextId++, author = "Me", published = "Now")) + posts
        } else {
            posts.map {
                if (it.id != post.id) it else it.copy(content = post.content)
            }
        }
        data.value = posts
        return TODO("Provide the return value")
    }

    override fun undo(post: Post) {
        data.value = posts
    }

}