package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): List<Post>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE PostEntity SET content=:text WHERE id=:id")
    fun edit(id: Long, text: String)

    fun save(post: PostEntity) = if (post.id == 0L) insert(post) else edit(post.id, post.content)

    @Query(
        """
            UPDATE PostEntity SET
               likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = :id;
        """
    )
    fun likeById(id: Long)

    @Query("DELETE FROM PostEntity WHERE id=:id")
    fun removeById(id: Long)

    @Query("""
        UPDATE PostEntity SET
               shared = shared + 1
           WHERE id = :id;
    """)
    fun increaseShare(id: Long)
}