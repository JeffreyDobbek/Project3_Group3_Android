package com.example.cst438_project03_group03.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.ArrayList;

/**
 * Class: AppDatabase.java
 * Description: Allows access to an instance of the Room database and singletons of the DAOs.
 */
@Database(entities = {User.class, Post.class, Option.class, Image.class, Comment.class}, version = 2, exportSchema = false)
@TypeConverters(ArrayListConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "AppDatabase";
    public static final String USER_TABLE = "user";
    public static final String POST_TABLE = "post";
    public static final String IMAGE_TABLE = "image";
    public static final String OPTION_TABLE = "option";
    public static final String COMMENT_TABLE = "comment";

    private static AppDatabase instance;

    public abstract UserDao userDao();
    //public abstract UserDao getDao();
    public abstract PostDao postDao();
    public abstract ImageDao imageDao();
    public abstract OptionDao optionDao();
    public abstract CommentDao commentDao();

    /**
     * Builds and returns an instance of the database.
     * @param context The application context.
     * @return an instance.
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
