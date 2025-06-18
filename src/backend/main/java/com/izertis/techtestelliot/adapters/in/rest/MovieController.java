package com.izertis.techtestelliot.adapters.in.rest;

import com.izertis.techtestelliot.application.port.in.QueryMovieUseCase;
import com.izertis.techtestelliot.domain.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final QueryMovieUseCase queryMovieUseCase;

    @GetMapping
    public ResponseEntity<List<Movie>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(queryMovieUseCase.searchByTitle(title));
    }

    //@GetMapping("/{id}")
    //public ResponseEntity<Movie> findByImdbId(@PathVariable String id) {
//
   //  }
}
