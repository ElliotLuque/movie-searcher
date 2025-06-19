package com.izertis.techtestelliot.adapters.out.inmemory;

import com.izertis.techtestelliot.application.port.out.MovieCatalog;
import com.izertis.techtestelliot.domain.model.Movie;
import com.izertis.techtestelliot.domain.model.MoviePage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "movies.provider", havingValue = "inmemory")
public class InMemoryMovieCatalogAdapter implements MovieCatalog {
    private final ConcurrentHashMap<String, Movie> store = new ConcurrentHashMap<>();

    public InMemoryMovieCatalogAdapter() {
        seed();
    }

    @Override
    public Mono<MoviePage> searchByTitle(String title, int page) {
        int pageSize = 10;

        List<Movie> all = store.values().stream()
                .filter(m -> m.title().toLowerCase().contains(title.toLowerCase()))
                .sorted((a,b) -> a.title().compareToIgnoreCase(b.title()))
                .collect(Collectors.toList());

        int total    = all.size();
        int fromIdx  = Math.min((page - 1) * pageSize, total);
        int toIdx    = Math.min(fromIdx + pageSize, total);
        List<Movie>  pageResults = new ArrayList<>(all.subList(fromIdx, toIdx));
        int totalPages = (int) Math.ceil(total / (double) pageSize);

        MoviePage mp = new MoviePage(page, totalPages, total, pageResults);
        return Mono.just(mp);
    }

    @Override
    public Mono<Movie> findByImdbId(String imdbId) {
        return Mono.justOrEmpty(store.get(imdbId));
    }

    // Helpers
    private void seed() {
        store.put("tt0076759", new Movie(
                "tt0076759",
                "Star Wars: Episode IV – A New Hope",
                1977,
                8.6,
                "George Lucas",
                "Farm boy Luke Skywalker joins a rebellion against the Galactic Empire.",
                "English",
                121,
                List.of("Action", "Adventure", "Sci-Fi")
        ));

        store.put("tt0080684", new Movie(
                "tt0080684",
                "Star Wars: Episode V – The Empire Strikes Back",
                1980,
                8.7,
                "Irvin Kershner",
                "The Imperial Forces pursue the Rebels; Luke trains with Jedi Master Yoda.",
                "English",
                124,
                List.of("Action", "Adventure", "Sci-Fi")
        ));

        store.put("tt0086190", new Movie(
                "tt0086190",
                "Star Wars: Episode VI – Return of the Jedi",
                1983,
                8.3,
                "Richard Marquand",
                "Luke confronts Darth Vader as the Rebel Alliance launches a decisive battle.",
                "English",
                131,
                List.of("Action", "Adventure", "Sci-Fi")
        ));

        store.put("tt2488496", new Movie(
                "tt2488496",
                "Star Wars: Episode VII – The Force Awakens",
                2015,
                7.8,
                "J. J. Abrams",
                "Thirty years after the defeat of the Empire, a new threat rises.",
                "English",
                138,
                List.of("Action", "Adventure", "Sci-Fi")
        ));

        store.put("tt0120915", new Movie(
                "tt0120915",
                "Star Wars: Episode I – The Phantom Menace",
                1999,
                6.5,
                "George Lucas",
                "Two Jedi uncover the Sith's return while protecting young Anakin Skywalker.",
                "English",
                136,
                List.of("Action", "Adventure", "Sci-Fi")
        ));

        store.put("tt0121766", new Movie(
                "tt0121766",
                "Star Wars: Episode III – Revenge of the Sith",
                2005,
                7.6,
                "George Lucas",
                "Anakin falls to the dark side as the Republic collapses into the Empire.",
                "English",
                140,
                List.of("Action", "Adventure", "Sci-Fi")
        ));

        store.put("tt0121765", new Movie(
                "tt0121765",
                "Star Wars: Episode II – Attack of the Clones",
                2002,
                6.6,
                "George Lucas",
                "Jedi investigate a clone army mystery amid growing separatist unrest.",
                "English",
                142,
                List.of("Action", "Adventure", "Sci-Fi")
        ));

        store.put("tt3748528", new Movie(
                "tt3748528",
                "Rogue One: A Star Wars Story",
                2016,
                7.8,
                "Gareth Edwards",
                "A band of rebels steals the Death Star plans in a daring mission.",
                "English",
                133,
                List.of("Action", "Adventure", "Sci-Fi")
        ));

        store.put("tt0796366", new Movie(
                "tt0796366",
                "Star Trek",
                2009,
                7.9,
                "J. J. Abrams",
                "James T. Kirk and Spock join the crew of the USS Enterprise on its maiden voyage.",
                "English",
                127,
                List.of("Action", "Adventure", "Sci-Fi")
        ));    }

    public void save(Movie movie) { store.put(movie.imdbId(), movie); }}
