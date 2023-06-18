package org.api.movieApi.controller;

import org.api.movieApi.entities.Movie;
import org.api.movieApi.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MovieControllerIT {

    @Autowired
    MovieController movieController;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    @Test
    void getMovieById() {
        Movie movie = movieRepository.findAll().get(0);

        Movie foundMovie = movieController.getMovieById(movie.getId());

        assertThat(foundMovie).isNotNull();
    }

    @Test
    void listMovies() {
        Page<Movie> movieList = movieController.listMovies(null, null, null,
                null, null, 0, 45463);

        assertThat(movieList.getContent().size()).isEqualTo(1000);
    }

    @Test
    @Transactional
    @Rollback
    void saveNewMovie() {
        Movie testMovie = Movie.builder()
                .originalTitle("kiwi lovers")
                .build();

        ResponseEntity response = movieController.saveNewMovie(testMovie);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(response.getHeaders().getLocation()).isNotNull();

        String[] id = response.getHeaders().getLocation().getPath().split("/");
        Long savedId = Long.valueOf(id[3]);

        Movie movie = movieRepository.findById(savedId).get();
        assertThat(movie).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void updateMovieById() {
        Movie movie = movieRepository.findAll().get(0);
        final String movieTitle = "UPDATED";
        movie.setTitle(movieTitle);

        ResponseEntity response = movieController.updateMovieById(movie.getId(), movie);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Movie updatedMovie = movieRepository.findById(movie.getId()).get();
        assertThat(updatedMovie.getTitle()).isEqualTo(movieTitle);
    }

    @Test
    @Transactional
    @Rollback
    void deleteMovieById() {
        Movie movie = movieRepository.findAll().get(0);

        ResponseEntity response = movieController.deleteMovieById(movie.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(movieRepository.findById(movie.getId())).isEmpty();
    }
}