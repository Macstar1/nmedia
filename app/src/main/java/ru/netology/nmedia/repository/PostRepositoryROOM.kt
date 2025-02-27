package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositoryROOM(
    private val dao: PostDao
) : PostRepository {


    override fun increaseShare(id: Long) {
        dao.increaseShare(id)
//        posts = posts.map {
//            if (it.id != id) it
//            else {
//                it.copy(shared = it.shared + 1)
//            }
//        }
//        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = dao.getAll().map { it.map {it.toDto()} }

    override fun save(post: Post) {
//        val id = post.id
//        val saved = dao.save(post)
//        posts = if (id == 0L) {
//            listOf(saved) + posts
//        } else {
//            posts.map {
//                if (it.id != id) it else saved
//            }
//        }
        dao.save(PostEntity.fromDto(post))
    }

    override fun undo(post: Post) {}

    override fun likeById(id: Long) = dao.likeById(id)

    override fun removeById(id: Long) = dao.removeById(id)

}