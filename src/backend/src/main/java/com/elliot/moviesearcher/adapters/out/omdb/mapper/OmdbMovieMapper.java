package com.elliot.moviesearcher.adapters.out.omdb.mapper;

import com.elliot.moviesearcher.adapters.out.omdb.dto.find.OmdbMovieDetailResponse;
import com.elliot.moviesearcher.domain.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OmdbMovieMapper {

   @Mapping(target = "genres", source = "genre", qualifiedByName = "parseGenres")
   @Mapping(target = "runtime", source = "runtime", qualifiedByName = "parseRuntime")
   @Mapping(target = "rating", source = "imdbRating", qualifiedByName = "parseRating")
   @Mapping(target = "imageUrl", source = "poster")
   Movie toDomain(OmdbMovieDetailResponse resp);

   // Helpers
   @Named("parseRuntime")
   default int parseRuntime(String number) {
      try {
         String[] split = number.split("\\s+");
         return Integer.parseInt(split[0]);
      } catch (NumberFormatException e) {
         return 0;
      }
   }

   @Named("parseRating")
   default double parseRating(String rating) {
      try {
         if (rating == null || rating.equals("N/A")) {
            return 0.0;
         } else {
            return Double.parseDouble(rating);
         }
      } catch (NumberFormatException e) {
         return 0.0;
      }
   }

   @Named("parseGenres")
   default List<String> parseGenres(String genres) {
      return genres == null
              ? List.of()
              : Arrays.stream(genres.split(",")).map(String::trim).toList();
   }
}
