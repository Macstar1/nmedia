package ru.netology.nmedia.repository

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryDao(private val db: SQLiteDatabase) : PostRepository {
    //    private var nextId = 1L
//
//    private var posts = listOf(
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий.",
//            published = "Сегодня",
//            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, дизайну, аналитике и управлению. Мы растём сами и помогаем студентам: от новичков до уверенных профессианалов. Но самое важноеостаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиься точнее, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
//            likedByMe = false,
//            likes = 992,
//          //  shared = 992,
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий.",
//            published = "Вчера",
//            content = "Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
//            likedByMe = false,
//            likes = 92,
////            shared = 92,
////            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий.",
//            published = "Сегодня",
//            content = "3. Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, дизайну, аналитике и управлению. Мы растём сами и помогаем студентам: от новичков до уверенных профессианалов. Но самое важноеостаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиься точнее, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
//            likedByMe = false,
//            likes = 992,
//        //    shared = 992,
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий.",
//            published = "Вчера",
//            content = "4. Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
//            likedByMe = false,
//            likes = 92,
//          //  shared = 92,
//        )
//
//    )
//
//    companion object {
//        val DDL = """
//        CREATE TABLE ${PostColumns.TABLE} (
//            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
//            ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
//            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
//            ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
//            ${PostColumns.COLUMN_LIKED_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
//            ${PostColumns.COLUMN_LIKE_COUNTER} INTEGER NOT NULL DEFAULT 0,
//            ${PostColumns.COLUMN_SHARED} INTEGER NOT NULL DEFAULT 0,
//            ${PostColumns.COLUMN_VIDEO} TEXT
//
//        );
//        """.trimIndent()
//    }
//
//    object PostColumns {
//        const val TABLE = "posts"
//        const val COLUMN_ID = "id"
//        const val COLUMN_AUTHOR = "author"
//        const val COLUMN_CONTENT = "content"
//        const val COLUMN_PUBLISHED = "published"
//        const val COLUMN_LIKED_BY_ME = "likedByMe"
//        const val COLUMN_LIKE_COUNTER = "likeCounter"
//        const val COLUMN_SHARED = "shared"
//        const val COLUMN_VIDEO = "video"
//
//        val ALL_COLUMNS = arrayOf(
//            COLUMN_ID,
//            COLUMN_AUTHOR,
//            COLUMN_CONTENT,
//            COLUMN_PUBLISHED,
//            COLUMN_LIKED_BY_ME,
//            COLUMN_LIKE_COUNTER,
//            COLUMN_SHARED,
//            COLUMN_VIDEO
//        )
//    }
//
//    private val data = MutableLiveData(posts)
//
//    private fun map(cursor: Cursor): Post {
//        with(cursor) {
//            return Post(
//                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
//                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
//                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
//                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
//                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED_BY_ME)) != 0,
//                likes = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKE_COUNTER)),
////                shared = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_SHARED)),
////                video = getString(getColumnIndexOrThrow(PostColumns.COLUMN_VIDEO))
//            )
//        }
//    }
//
//    override fun increaseShare(id: Long) {}
//
//
//    override fun getAll(): List<Post> {
//        val posts = mutableListOf<Post>()
//        db.query(
//            PostColumns.TABLE,
//            PostColumns.ALL_COLUMNS,
//            null,
//            null,
//            null,
//            null,
//            "${PostColumns.COLUMN_ID} DESC"
//        ).use {
//            while (it.moveToNext()) {
//                posts.add(map(it))
//            }
//        }
//        return data
//    }
//
//    override fun likeById(id: Long) {
//        db.execSQL(
//            """
//           UPDATE posts SET
//               likeCounter = likeCounter + CASE WHEN likedByMe THEN -1 ELSE 1 END,
//               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
//           WHERE id = ?;
//        """.trimIndent(), arrayOf(id)
//        )
//    }
//
//    override fun removeById(id: Long) {
//        db.delete(
//            PostColumns.TABLE,
//            "${PostColumns.COLUMN_ID} = ?",
//            arrayOf(id.toString())
//        )
//    }
//
//    var post = Post()
//    override fun save(post: Post) {
//        val values = ContentValues().apply {
//            // TODO: remove hardcoded values
//            put(PostColumns.COLUMN_AUTHOR, "Me")
//            put(PostColumns.COLUMN_CONTENT, post.content)
//            put(PostColumns.COLUMN_PUBLISHED, "now")
//        }
//        val id = if (post.id != 0L) {
//            db.update(
//                PostColumns.TABLE,
//                values,
//                "${PostColumns.COLUMN_ID} = ?",
//                arrayOf(post.id.toString()),
//            )
//            post.id
//        } else {
//            db.insert(PostColumns.TABLE, null, values)
//        }
//        db.query(
//            PostColumns.TABLE,
//            PostColumns.ALL_COLUMNS,
//            "${PostColumns.COLUMN_ID} = ?",
//            arrayOf(id.toString()),
//            null,
//            null,
//            null,
//        ).use {
//            it.moveToNext()
//            this.post = map(it)
//        }
//    }
//
//
//    override fun undo(post: Post) {
//        TODO("Not yet implemented")
//    }
//
//
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
}