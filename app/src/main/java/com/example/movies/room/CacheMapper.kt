package com.example.movies.room

import com.example.movies.model.Image
import com.example.movies.model.MovieResponseDto
import com.example.movies.util.EntityMapper
import javax.inject.Inject

class CacheMapper @Inject constructor() : EntityMapper<CacheMovieDto, MovieResponseDto> {

    override fun mapFromEntity(entity: CacheMovieDto): MovieResponseDto =
        MovieResponseDto(
            id = entity.id,
            image = Image(entity.image),
            language = entity.language,
            name = entity.name
        )

    override fun mapToEntity(domainModel: MovieResponseDto): CacheMovieDto =
        CacheMovieDto(
            id = domainModel.id,
            image = domainModel.image.original,
            language = domainModel.language,
            name = domainModel.name
        )

    fun mapFromEntityList(entities: List<CacheMovieDto>): List<MovieResponseDto> =
        entities.map { mapFromEntity(it) }
}