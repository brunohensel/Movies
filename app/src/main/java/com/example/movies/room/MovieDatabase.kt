package com.example.movies.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CacheMovieDto::class], version = 1)
abstract class MovieDatabase : RoomDatabase(){

    abstract fun movieDao(): MovieDao

    companion object{
        const val DATABASE_NAME: String = "movie_db"
    }
}