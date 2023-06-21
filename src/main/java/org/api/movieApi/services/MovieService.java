package org.api.movieApi.services;


import org.api.movieApi.controller.HttpNotFoundException;
import org.api.movieApi.entities.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    //List<Movie> listMovies(String movieTitle, double runtime, String originalLanguage, String genre, bo adult);
    List<Movie> listMovies();

    Optional<Movie> getMovieById(Long id) throws HttpNotFoundException;

    Movie saveNewMovie(Movie movie);

    Optional<Movie> updateMovieById(Long id, Movie movie);

    Boolean deleteById(Long id);

}
