package ru.netology.nmedia.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo()
    val content: String = "",
    val published: String = "",
    val author: String = "",
    val authorAvatar: String = "",
    var likedByMe: Boolean = false,
    val likes: Int = 0,
    val shared: Int = 0,
    val video: String = "",
    ) {
//    fun toDto() = Post(
//        id,
//        content,
//        published,
//        author,
//        likedByMe,
//        likes,
////        shared,
////        video
 //   )

    companion object {
        fun fromDto(post: Post) = PostEntity(
            post.id,
            post.content,
            post.published,
            post.author,
            post.authorAvatar,
            post.likedByMe,
            post.likes,
            post.shared,
            post.video
        )
    }
}