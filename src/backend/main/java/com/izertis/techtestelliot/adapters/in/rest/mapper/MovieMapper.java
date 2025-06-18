package com.izertis.techtestelliot.adapters.in.rest.mapper;

import com.izertis.techtestelliot.adapters.in.rest.dto.MovieDetailResponse;
import com.izertis.techtestelliot.adapters.in.rest.dto.MoviePageItemDTO;
import com.izertis.techtestelliot.domain.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MoviePageItemDTO toPageItem(Movie movie);

    MovieDetailResponse toDetail(Movie movie);
}
