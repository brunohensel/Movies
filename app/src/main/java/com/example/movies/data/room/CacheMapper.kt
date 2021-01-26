package com.example.movies.data.room

import com.example.movies.domain.Image
import com.example.movies.domain.MRating
import com.example.movies.domain.MovieDto
import com.example.movies.domain.movie.CacheMovieDto
import com.example.movies.util.EntityMapper
import javax.inject.Inject

/**Mapper the cached object into the model object [MovieDto]*/
class CacheMapper @Inject constructor() : EntityMapper<CacheMovieDto, MovieDto> {

    override fun mapFromEntity(entity: CacheMovieDto): MovieDto =
        MovieDto(
            id = entity.id,
            image = Image(entity.image),
            language = entity.language,
            name = entity.name,
            genres = entity.genres,
            summary = entity.summary,
            rating = MRating(entity.rating)
        )

    override fun mapToEntity(domainModel: MovieDto): CacheMovieDto =
        CacheMovieDto(
            id = domainModel.id,
            image = domainModel.image.original,
            language = domainModel.language,
            name = domainModel.name,
            genres = domainModel.genres,
            summary = domainModel.summary,
            rating = domainModel.rating.average
        )

    fun mapFromEntityList(entities: List<CacheMovieDto>): List<MovieDto> =
        entities.map { mapFromEntity(it) }
}