package com.izertis.techtestelliot.domain.model;

import java.util.List;

public record Movie(
        String imdbId,
        String title,
        int year,
        double rating,
        String director,
        String plot,
        int runtime,
        List<String> genres
) {
}
