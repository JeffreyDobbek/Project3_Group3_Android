package com.example.cst438_project03_group03.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ImageDao {
    @Insert
    void insert(Image... images);

    @Update
    void update(Image... images);

    @Delete
    void delete(Image image);

    @Query("SELECT * FROM " + RoomDatabase.IMAGE_TABLE + " WHERE postId = :postId ORDER BY orders DESC")
    LiveData<List<Image>> getImagesByPostId(int postId);

    @Query("DELETE FROM " + RoomDatabase.IMAGE_TABLE)
    void deleteAllImages();
}
