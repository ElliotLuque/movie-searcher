package com.izertis.techtestelliot.adapters.out.omdb;

import com.izertis.techtestelliot.adapters.out.omdb.mapper.OmdbMovieMapper;
import com.izertis.techtestelliot.adapters.out.omdb.mapper.OmdbMoviePageMapper;
import com.izertis.techtestelliot.domain.exception.MovieNotFoundException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OmdbMovieCatalogAdapterTest {
    private static MockWebServer server;
    private OmdbMovieCatalogAdapter adapter;

    @BeforeAll
    static void startServer() throws Exception {
        server = new MockWebServer();
        server.start();
    }

    @AfterAll
    static void shutdownServer() throws Exception {
        server.shutdown();
    }

    @BeforeEach
    void setUp() {
        String baseUrl = server.url("/").toString();
        WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        OmdbMovieMapper movieMapper = Mappers.getMapper(OmdbMovieMapper.class);
        OmdbMoviePageMapper pageMapper = Mappers.getMapper(OmdbMoviePageMapper.class);
        adapter = new OmdbMovieCatalogAdapter(client, movieMapper, pageMapper);
    }

    @Test
    void whenSearchByTitleMatrix_returnsCorrectMoviePage() {
        String json = """
          {
            "Search":[
              {
                "imdbID":"tt0133093",
                "Title":"The Matrix",
                "Year":"1999",
                "Poster":"https://…/matrix.jpg"
              },
              {
                "imdbID":"tt0234215",
                "Title":"The Matrix Reloaded",
                "Year":"2003",
                "Poster":"https://…/matrix2.jpg"
              }
            ],
            "totalResults":"2",
            "Response":"True"
          }
          """;
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(json));

        StepVerifier.create(adapter.searchByTitle("Matrix", 1))
                .assertNext(page -> {
                    assertEquals(1, page.page());
                    assertEquals(2, page.totalElements());
                    assertEquals(1, page.totalPages());  // según cálculo: ceil(2/10) = 1
                    assertEquals(2, page.results().size());

                    assertEquals("tt0133093", page.results().getFirst().imdbId());
                    assertEquals("The Matrix", page.results().get(0).title());
                    assertEquals(1999, page.results().get(0).year());

                    assertEquals("tt0234215", page.results().get(1).imdbId());
                    assertEquals("The Matrix Reloaded", page.results().get(1).title());
                    assertEquals(2003, page.results().get(1).year());
                })
                .verifyComplete();
    }

    @Test
    void whenfindByImdbId_returnsCorrectMovieDetail() {
        String jsonDetail = """
        {
            "imdbID":"tt0133093",
            "Title":"The Matrix",
            "Year":"1999",
            "Poster":"https://example.com/matrix.jpg",
            "Director":"Lana Wachowski, Lilly Wachowski",
            "Plot":"A computer hacker learns from mysterious rebels about the true nature of his reality.",
            "Genre":"Action, Sci-Fi",
            "Language":"English",
            "Runtime":"136 min",
            "Response":"True"
        }
        """;
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(jsonDetail));

        StepVerifier.create(adapter.findByImdbId("tt0133093"))
                .assertNext(movie -> {
                    assertEquals("tt0133093", movie.imdbId());
                    assertEquals("The Matrix",    movie.title());
                    assertEquals(1999,            movie.year());
                    assertEquals("Lana Wachowski, Lilly Wachowski", movie.director());
                    assertTrue(movie.plot().startsWith("A computer hacker"));
                })
                .verifyComplete();
    }

    @Test
    void whenImdbIdNotExists_throwMovieNotFoundException() {
        String jsonError = """
        {
            "Response":"False",
            "Error":"Movie not found!"
        }
        """;
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(jsonError));

        StepVerifier.create(adapter.findByImdbId("tt0000000"))
                .expectErrorSatisfies(err -> {
                    assertInstanceOf(MovieNotFoundException.class, err);
                    assertTrue(err.getMessage().contains("tt0000000"));
                })
                .verify();
    }
}
