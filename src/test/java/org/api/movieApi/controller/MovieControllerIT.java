package org.api.movieApi.controller;

import org.api.movieApi.entities.Movie;
import org.api.movieApi.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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

        Movie foundMovie = movieRepository.findById(movie.getId()).get();

        assertThat(foundMovie).isNotNull();
    }

    @Test
    void listMovies() {
    }

    @Test
    void saveNewMovie() {
    }

    @Test
    void updateMovieById() {
    }

    @Test
    void deleteMovieById() {
    }
}