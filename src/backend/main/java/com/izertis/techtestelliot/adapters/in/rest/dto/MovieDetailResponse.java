package com.izertis.techtestelliot.adapters.in.rest.dto;

import java.util.List;

public record MovieDetailResponse(
        String imdbId,
        String title,
        int year,
        String director,
        double rating,
        String plot,
        List<String> genres,
        String language,
        int runtime
) {
}
