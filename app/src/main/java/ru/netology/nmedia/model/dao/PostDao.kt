package ru.netology.nmedia.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE PostEntity SET content = :content WHERE id = :id")
    fun updateContentById(id: Long, content: String)

    fun save(post: PostEntity) =
        if (post.id == 0L) insert(post) else updateContentById(post.id, post.content)

    @Query(
        """
        UPDATE PostEntity SET
                likesCount = likesCount + CASE WHEN likedByMe THEN -1 ELSE 1 END,
        likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
                WHERE id = :id
                """)
    fun likedById(id: Long)

    @Query("UPDATE PostEntity SET repostSum = repostSum + 1 WHERE id = :id")
    fun repostById(id: Long)

    @Query("UPDATE PostEntity SET urlVideo = :text WHERE id = :id")
    fun onPlayVideo(text: String, id: Long)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removById(id: Long)

}