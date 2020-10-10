package com.example.movies.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movies.domain.movie.CacheMovieDto
import com.example.movies.domain.movie.Converters

/**Annotates class to be a Room Database with a table (entity) of the Word class*/

@Database(entities = [CacheMovieDto::class], version = 3)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME: String = "movie_db"
    }
}