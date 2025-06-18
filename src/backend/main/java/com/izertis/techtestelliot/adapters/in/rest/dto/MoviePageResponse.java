package com.izertis.techtestelliot.adapters.in.rest.dto;

import java.util.List;

public record MoviePageResponse(
        int page,
        int totalPages,
        int totalElements,
        List<MoviePageItemDTO> results
) {

}
