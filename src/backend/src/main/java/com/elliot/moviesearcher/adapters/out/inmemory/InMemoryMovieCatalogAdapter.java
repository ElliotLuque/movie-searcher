package com.elliot.moviesearcher.adapters.out.inmemory;

import com.elliot.moviesearcher.application.port.out.MovieCatalog;
import com.elliot.moviesearcher.domain.model.Movie;
import com.elliot.moviesearcher.domain.model.MoviePage;
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

        MoviePage mp = new MoviePage(page, pageSize, totalPages, total, pageResults);
        return Mono.just(mp);
    }

    @Override
    public Mono<Movie> findByImdbId(String imdbId) {
        return Mono.justOrEmpty(store.get(imdbId));
    }

    // Helpers
    public void seed() {
        store.put("tt0076759", new Movie(
                "tt0076759",
                "Star Wars: Episode IV – A New Hope",
                "https://upload.wikimedia.org/wikipedia/en/8/87/StarWarsMoviePoster1977.jpg",
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
                "https://upload.wikimedia.org/wikipedia/en/3/3f/The_Empire_Strikes_Back_%281980_film%29.jpg",
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
                "https://upload.wikimedia.org/wikipedia/en/b/b2/ReturnOfTheJediPoster1983.jpg",
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
                "https://upload.wikimedia.org/wikipedia/en/a/a2/Star_Wars_The_Force_Awakens_Theatrical_Poster.jpg",
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
                "https://upload.wikimedia.org/wikipedia/en/4/40/Star_Wars_Phantom_Menace_poster.jpg",
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
                "https://upload.wikimedia.org/wikipedia/en/9/93/Star_Wars_Episode_III_Revenge_of_the_Sith_poster.jpg",
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
                "https://upload.wikimedia.org/wikipedia/en/3/32/Star_Wars_-_Episode_II_Attack_of_the_Clones_%28movie_poster%29.jpg",
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
                "https://upload.wikimedia.org/wikipedia/en/d/d4/Rogue_One%2C_A_Star_Wars_Story_poster.png",
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
                "https://www.themoviedb.org/t/p/original/rv1AWImgx386ULjNB06vD1nfVLu.jpg",
                2009,
                7.9,
                "J. J. Abrams",
                "James T. Kirk and Spock join the crew of the USS Enterprise on its maiden voyage.",
                "English",
                127,
                List.of("Action", "Adventure", "Sci-Fi")
        ));
    }
}
