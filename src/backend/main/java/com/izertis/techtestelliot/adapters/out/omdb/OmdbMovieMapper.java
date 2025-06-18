package com.izertis.techtestelliot.adapters.out.omdb;

import com.izertis.techtestelliot.adapters.out.omdb.dto.OmdbMovieDetailResponse;
import com.izertis.techtestelliot.domain.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OmdbMovieMapper {

   @Mapping(target = "year", source = "year", qualifiedByName = "parseYear")
   @Mapping(target = "rating", source = "imdbRating", qualifiedByName = "parseRating")
   @Mapping(target = "genres", source = "genre", qualifiedByName = "parseGenres")
   @Mapping(target = "runtime", expression = "java(parseRuntime(dto.runtime()))")
   Movie toDomain(OmdbMovieDetailResponse dto);

   // Helpers
   @Named("parseYear")
   default int parseYear(String year) {
      if (year == null) return 0;
      String digits = year.replaceAll("\\D", "");
      if (digits.length() < 4) return 0;
      return Integer.parseInt(digits.substring(0, 4));
   }

   @Named("parseRating")
   default double parseRating(String number) {
      try {
         return Double.parseDouble(number);
      } catch (NumberFormatException e) {
         return 0.0;
      }
   }

   @Named("parseRuntime")
   default int parseRuntime(String number) {
      try {
         String[] split = number.split("\\s+");
         return Integer.parseInt(split[0]);
      } catch (NumberFormatException e) {
         return 0;
      }
   }

   @Named("parseGenres")
   default List<String> parseGenres(String genres) {
      return genres == null
              ? List.of()
              : Arrays.stream(genres.split(",")).map(String::trim).toList();
   }
}
