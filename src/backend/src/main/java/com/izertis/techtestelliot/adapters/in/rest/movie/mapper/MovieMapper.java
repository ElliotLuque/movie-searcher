package com.izertis.techtestelliot.adapters.in.rest.movie.mapper;

import com.izertis.techtestelliot.adapters.in.rest.movie.dto.MovieDetailResponse;
import com.izertis.techtestelliot.adapters.in.rest.movie.dto.MoviePageItemDTO;
import com.izertis.techtestelliot.domain.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MoviePageItemDTO toPageItem(Movie movie);

    MovieDetailResponse toDetail(Movie movie);
}
