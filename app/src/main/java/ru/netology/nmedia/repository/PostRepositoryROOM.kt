package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositoryROOM(
    private val dao: PostDao
) : PostRepository {
    //
//
//    override fun increaseShare(id: Long) {
//        dao.increaseShare(id)
////        posts = posts.map {
////            if (it.id != id) it
////            else {
////                it.copy(shared = it.shared + 1)
////            }
////        }
////        data.value = posts
//    }
//
//    override fun getAll(): List<Post> = dao.getAll().map { it.map {it.toDto()} } as List<Post>
//
//    override fun save(post: Post): Post {
////        val id = post.id
////        val saved = dao.save(post)
////        posts = if (id == 0L) {
////            listOf(saved) + posts
////        } else {
////            posts.map {
////                if (it.id != id) it else saved
////            }
////        }
//        dao.save(PostEntity.fromDto(post))
//        return TODO("Provide the return value")
//    }
//
//    override fun undo(post: Post) {}
//
//    override fun likeById(id: Long): Post //= dao.likeById(id)
//    {
//        TODO("Provide the return value")
//    }
//
//    override fun unlikeById(id: Long): Post {
//        TODO("Not yet implemented")
//    }
//
//    override fun removeById(id: Long) = dao.removeById(id)
    override fun increaseShare(id: Long) {
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