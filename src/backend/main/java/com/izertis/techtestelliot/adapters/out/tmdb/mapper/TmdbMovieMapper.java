package com.izertis.techtestelliot.adapters.out.tmdb.mapper;

import com.izertis.techtestelliot.adapters.out.tmdb.dto.find.TmdbMovieDetailResponse;
import com.izertis.techtestelliot.domain.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring")
public interface TmdbMovieMapper {
    @Mapping(target = "year", source = "releaseDate", qualifiedByName = "extractYear")
    @Mapping(target = "rating", source = "voteAverage")
    @Mapping(target = "plot", source = "overview")
    @Mapping(target = "language", source = "originalLanguage", qualifiedByName = "mapLanguageToDisplayName")
    @Mapping(target = "genres", expression = "java(dto.genres().stream().map(g -> g.name()).toList())")
    @Mapping(target = "director", source = "credits.crew", qualifiedByName = "extractDirector")
    Movie toDomain(TmdbMovieDetailResponse dto);

    @Named("extractYear")
    default int extractYear(String date) {
        return (date != null && date.length() >= 4) ? Integer.parseInt(date.substring(0, 4)) : 0;
    }

    @Named("mapLanguageToDisplayName")
    default String mapLanguageToDisplayName(String languageCode) {
        if (languageCode == null || languageCode.isBlank()) return "Unknown";

        Locale locale = Locale.forLanguageTag(languageCode);
        return locale.getDisplayLanguage(Locale.ENGLISH);
    }
    @Named("extractDirector")
    default String extractDirector(List<TmdbMovieDetailResponse.Credits.CrewMember> crew) {
        if (crew == null) return "Unknown";
        return crew.stream()
                .filter(c -> c.job().equalsIgnoreCase("Director"))
                .map(TmdbMovieDetailResponse.Credits.CrewMember::name)
                .findFirst()
                .orElse("Unknown");
    }}
