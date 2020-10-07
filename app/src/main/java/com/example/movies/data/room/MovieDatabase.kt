package com.example.movies.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.domain.CacheMovieDto

@Database(entities = [CacheMovieDto::class], version = 1)
abstract class MovieDatabase : RoomDatabase(){

    abstract fun movieDao(): MovieDao

    companion object{
        const val DATABASE_NAME: String = "movie_db"
    }
}