package com.elliot.moviesearcher.domain.model;

import java.util.List;

public record Movie(
        String imdbId,
        String title,
        String imageUrl,
        int year,
        double rating,
        String director,
        String plot,
        String language,
        int runtime,
        List<String> genres
) {
}
