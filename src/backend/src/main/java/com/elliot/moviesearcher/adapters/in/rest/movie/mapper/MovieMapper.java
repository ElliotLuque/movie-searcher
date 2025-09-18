package com.elliot.moviesearcher.adapters.in.rest.movie.mapper;

import com.elliot.moviesearcher.adapters.in.rest.movie.dto.MovieDetailResponse;
import com.elliot.moviesearcher.adapters.in.rest.movie.dto.MoviePageItemDTO;
import com.elliot.moviesearcher.domain.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MoviePageItemDTO toPageItem(Movie movie);

    MovieDetailResponse toDetail(Movie movie);
}
