package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post

class PostRepositorySQL(
    private val dao: PostDao
) : PostRepository {

    //    private var nextId = 1L
//    private var posts = listOf(
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий.",
//            published = "Сегодня",
//            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, дизайну, аналитике и управлению. Мы растём сами и помогаем студентам: от новичков до уверенных профессианалов. Но самое важноеостаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиься точнее, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
//            likedByMe = false,
//            likes = 992,
//            shared = 992,
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий.",
//            published = "Вчера",
//            content = "Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
//            likedByMe = false,
//            likes = 92,
//            shared = 92,
//            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий.",
//            published = "Сегодня",
//            content = "3. Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, дизайну, аналитике и управлению. Мы растём сами и помогаем студентам: от новичков до уверенных профессианалов. Но самое важноеостаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиься точнее, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
//            likedByMe = false,
//            likes = 992,
//            shared = 992,
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий.",
//            published = "Вчера",
//            content = "4. Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
//            likedByMe = false,
//            likes = 92,
//            shared = 92,
//        )
//
//    )
//
//    private val data = MutableLiveData(posts)
//
//    init {
//        posts = dao.getAll()
//        data.value = posts
//    }
//
//    override fun increaseShare(id: Long) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getAll(): LiveData<List<Post>> = data
//
//    override fun save(post: Post) {
//        val id = post.id
//        val saved = dao.save(post)
//        posts = if (id == 0L) {
//            listOf(saved) + posts
//        } else {
//            posts.map {
//                if (it.id != id) it else saved
//            }
//        }
//        data.value = posts
//    }
//
//    override fun undo(post: Post) {
//        data.value = posts
//    }
//
//    override fun likeById(id: Long) {
//        dao.likeById(id)
//        posts = posts.map {
//            if (it.id != id) it else it.copy(
//                likedByMe = !it.likedByMe,
//                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
//            )
//        }
//        data.value = posts
//    }
//
//    override fun removeById(id: Long) {
//        dao.removeById(id)
//        posts = posts.filter { it.id != id }
//        data.value = posts
//    }
    override fun increaseShare(id: Long) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Post> {
        TODO("Not yet implemented")
    }

    override fun likeById(id: Long): Post {
        TODO("Not yet implemented")
    }

    override fun unlikeById(id: Long): Post {
        TODO("Not yet implemented")
    }

    override fun removeById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun save(post: Post): Post {
        TODO("Not yet implemented")
    }

    override fun undo(post: Post) {
        TODO("Not yet implemented")
    }

    override fun getAllAsync(callback: PostRepository.GetAllCallback) {
        TODO("Not yet implemented")
    }

    override fun saveAsync(callback: PostRepository.SaveCallback, post: Post) {
        TODO("Not yet implemented")
    }

    override fun removeByIdAsync(callback: PostRepository.RemoveByIdCallback, id: Long) {
        TODO("Not yet implemented")
    }

    override fun unlikeByIdAsync(callback: PostRepository.LikeByIdCallback, id: Long) {
        TODO("Not yet implemented")
    }

    override fun likeByIdAsync(callback: PostRepository.LikeByIdCallback, id: Long) {
        TODO("Not yet implemented")
    }
}