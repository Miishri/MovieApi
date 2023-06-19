package org.api.movieApi.services;


import org.api.movieApi.entities.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<Movie> listMovies(String movieTitle, Double runtime, String originalLanguage, String genre, Boolean adult);

    Optional<Movie> getMovieById(Long id);

    Movie saveNewMovie(Movie movie);

    Optional<Movie> updateMovieById(Long id, Movie movie);

    Boolean deleteById(Long id);

}
