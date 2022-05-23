package com.example.cst438_project03_group03.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {
    @Insert
    void insert(Post... posts);

    @Update
    void update(Post... posts);

    @Delete
    void delete(Post post);

    @Query("SELECT * FROM " + RoomDatabase.POST_TABLE + " WHERE userId = :userId ORDER BY postId DESC")
    LiveData<List<Post>> getUserPosts(int userId);

    @Query("SELECT * FROM " + RoomDatabase.POST_TABLE + " ORDER BY postId DESC")
    LiveData<List<Post>> getAllPosts();

    @Query("DELETE FROM " + RoomDatabase.POST_TABLE)
    void deleteAllPosts();
}
