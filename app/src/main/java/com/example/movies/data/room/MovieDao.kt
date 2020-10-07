package com.example.movies.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.domain.CacheMovieDto

@Dao
interface MovieDao {
    /**The [Long] return means which row the object was inserted to */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cacheMovieDto: CacheMovieDto): Long

    @Query("SELECT * from movies")
    suspend fun get(): List<CacheMovieDto>
}