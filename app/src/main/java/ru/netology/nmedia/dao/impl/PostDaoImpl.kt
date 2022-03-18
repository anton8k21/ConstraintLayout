package ru.netology.nmedia.dao.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.Post
import ru.netology.nmedia.dao.PostDao

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {
    companion object {
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
            ${PostColumns.COLUMN_URL_VIDEO} TEXT NOT NULL,
            ${PostColumns.COLUMN_LIKES_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_LIKES_COUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_REPOST_SUM} INTEGER NOT NULL DEFAULT 0
        );
        """.trimIndent()
    }
    override fun get(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
                UPDATE posts SET
                likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
                likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
                WHERE id = ?;
                """.trimIndent(), arrayOf(id)
        )
    }

    override fun repostById(id: Long) {
        db.execSQL(
            """
                UPDATE posts SET
                repostSum = repostSum + 1
                WHERE id = ?
                """.trimIndent(), arrayOf(id)
        )

    }

    override fun removeById(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    override fun save(post: Post): Post {

        val values = ContentValues().apply {
            if (post.id != 0L) {
                put(PostColumns.COLUMN_ID, post.id)
            }
            put(PostColumns.COLUMN_AUTHOR, "Me")
            put(PostColumns.COLUMN_CONTENT, post.content)
            put(PostColumns.COLUMN_PUBLISHED, "new")
            put(PostColumns.COLUMN_URL_VIDEO, "")
        }
        val id = db.replace(PostColumns.TABLE, null, values)
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

    override fun onPlayVideo(text: String, id: Long) {
        db.execSQL(
            """ 
                UPDATE posts SET
                urlVideo = "${text}"
                WHERE id = ?
            """.trimIndent(), arrayOf(id)
        )
    }

    private fun map(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                urlVideo = getString(getColumnIndexOrThrow(PostColumns.COLUMN_URL_VIDEO)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES_BY_ME)) != 0,
                likesCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES_COUNT)),
                repostSum = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_REPOST_SUM))
            )
        }
    }

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_URL_VIDEO = "urlVideo"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_LIKES_BY_ME = "likedByMe"
        const val COLUMN_LIKES_COUNT = "likesCount"
        const val COLUMN_REPOST_SUM = "repostSum"
        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_URL_VIDEO,
            COLUMN_PUBLISHED,
            COLUMN_CONTENT,
            COLUMN_LIKES_BY_ME,
            COLUMN_LIKES_COUNT,
            COLUMN_REPOST_SUM
        )
    }
}