package com.example.movies.domain

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**Each [@Entity] class represents a SQLite table.
 * Every entity needs a primary key.
 * [@ColumnInfo] Specifies the name of the column in the table*/

@Keep
@Entity(tableName = "movies")
class CacheMovieDto(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "language")
    val language: String,

    @ColumnInfo(name = "name")
    val name: String
)