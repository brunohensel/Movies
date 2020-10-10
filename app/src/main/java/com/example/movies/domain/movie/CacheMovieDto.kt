package com.example.movies.domain.movie

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**Each [@Entity] class represents a SQLite table.
 * Every entity needs a primary key.
 * [@ColumnInfo] Specifies the name of the column in the table*/

@Keep
@Entity(tableName = "movies")
data class CacheMovieDto(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "language")
    val language: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "genres")
    val genres: List<String>,

    @ColumnInfo(name = "summary")
    val summary: String,

    @ColumnInfo(name = "rating")
    val rating: Double
){
    companion object{
        val empty = CacheMovieDto(-1, "", "","", emptyList(), "", 0.0)
    }
}

class Converters {
    @TypeConverter
    fun listToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun fromString(value: String?): List<String?>? {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
}