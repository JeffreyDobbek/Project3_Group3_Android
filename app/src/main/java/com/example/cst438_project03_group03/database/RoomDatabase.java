package com.example.cst438_project03_group03.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.TypeConverters;

/**
 * Class: RoomDatabase.java
 * Description: Allows access to an instance of the Room database and singletons of the DAOs.
 */
@Database(entities = {User.class, Post.class, Image.class}, version = 3, exportSchema = false)
@TypeConverters(ArrayListConverter.class)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {

    public static final String DATABASE_NAME = "RoomDatabase";
    public static final String USER_TABLE = "user";
    public static final String POST_TABLE = "post";
    public static final String IMAGE_TABLE = "image";

    private static RoomDatabase instance;

    public abstract UserDao userDao();
    public abstract PostDao postDao();
    public abstract ImageDao imageDao();

    /**
     * Builds and returns an instance of the database.
     * @param context The application context.
     * @return an instance.
     */
    public static synchronized RoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, RoomDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
