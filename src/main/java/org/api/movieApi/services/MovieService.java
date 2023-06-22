package org.api.movieApi.services;


import org.api.movieApi.controller.HttpNotFoundException;
import org.api.movieApi.entities.Movie;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface MovieService {

    Page<Movie> listMovies(Integer pageNumber, Integer pageSize);

    Optional<Movie> getMovieById(Long id) throws HttpNotFoundException;

    Movie saveNewMovie(Movie movie);

    Optional<Movie> updateMovieById(Long id, Movie movie);

    Boolean deleteById(Long id);

}
