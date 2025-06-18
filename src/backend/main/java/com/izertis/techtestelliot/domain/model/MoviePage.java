package com.izertis.techtestelliot.domain.model;

import java.util.List;

public record MoviePage(
        int page,
        int totalPages,
        int totalElements,
        List<Movie> results
) {
}
