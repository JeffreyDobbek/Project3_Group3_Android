package com.example.cst438_project03_group03.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE userId = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE email = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " ORDER BY name DESC")
    List<User> getAllUsers();
}
