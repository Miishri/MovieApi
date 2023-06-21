package org.api.movieApi.controller;

import org.api.movieApi.entities.Movie;
import org.api.movieApi.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MovieControllerIT {

    @Autowired
    MovieController movieController;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    WebApplicationContext wac;

    @Test
    void getMovieById() throws HttpNotFoundException {
        Movie movie = movieRepository.findAll().get(0);

        Movie foundMovie = movieController.getMovieById(movie.getId());

        assertThat(foundMovie).isNotNull();
    }

    @Test
    void testGetMovieByIdNotFound() {
        assertThrows(HttpNotFoundException.class, () -> {
            movieController.getMovieById(999999999L);
        });
    }
    @Test
    @Transactional
    @Rollback
    void saveNewMovie() {
        Movie testMovie = Movie.builder()
                .originalTitle("kiwi lovers")
                .build();

        Movie movie = movieController.saveNewMovie(testMovie);

        assertThat(movie.getOriginalTitle()).isEqualTo(testMovie.getOriginalTitle());
        assertThat(movie).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void updateMovieById() throws HttpNotFoundException {
        Movie movie = movieRepository.findAll().get(0);
        final String movieTitle = "UPDATED";
        movie.setTitle(movieTitle);

        ResponseEntity<Movie> response = movieController.updateMovieById(movie.getId(), movie);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Movie updatedMovie = movieRepository.findById(movie.getId()).get();
        assertThat(updatedMovie.getTitle()).isEqualTo(movieTitle);
    }

    @Test
    void testUpdateMovieByIdNotFound() {
        assertThrows(HttpNotFoundException.class, () -> {
           movieController.updateMovieById(999999999L, Movie.builder().build());
        });
    }

    @Test
    @Transactional
    @Rollback
    void deleteMovieById() throws HttpNotFoundException {
        Movie movie = movieRepository.findAll().get(0);

        ResponseEntity<Void> response = movieController.deleteMovieById(movie.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(movieRepository.findById(movie.getId())).isEmpty();
    }

    @Test
    void testDeleteMovieByIdNotFound() {
        assertThrows(HttpNotFoundException.class, () -> {
            movieController.deleteMovieById(999999999L);
        });
    }
}