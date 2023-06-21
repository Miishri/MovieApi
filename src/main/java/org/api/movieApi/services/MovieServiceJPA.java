package org.api.movieApi.services;

import lombok.RequiredArgsConstructor;
import org.api.movieApi.controller.HttpNotFoundException;
import org.api.movieApi.entities.Movie;
import org.api.movieApi.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MovieServiceJPA implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> getMovieById(Long id) throws HttpNotFoundException {
        return Optional.ofNullable(movieRepository.findById(id).orElseThrow(HttpNotFoundException::new));
    }

    @Override
    public Movie saveNewMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Optional<Movie> updateMovieById(Long id, Movie movie) {

        var foundMovie = movieRepository.findById(id).orElse(null);

        if (foundMovie == null) {
            return Optional.empty();
        }else {
            foundMovie.setTitle(movie.getTitle());
            foundMovie.setOriginalTitle(movie.getOriginalTitle());
            foundMovie.setOverview(movie.getOverview());
            foundMovie.setGenres(movie.getGenres());
            foundMovie.setReleaseDate(movie.getReleaseDate());
            foundMovie.setRuntime(movie.getRuntime());
            foundMovie.setOriginalLanguage(movie.getOriginalLanguage());
            foundMovie.setPopularity(movie.getPopularity());
            foundMovie.setStatus(movie.getStatus());
            foundMovie.setVoteAverage(movie.getVoteAverage());
            foundMovie.setVoteCount(movie.getVoteCount());
            foundMovie.setBudget(movie.getBudget());
            foundMovie.setRevenue(movie.getRevenue());
            foundMovie.setHomepage(movie.getHomepage());
            foundMovie.setImdbId(movie.getImdbId());
            foundMovie.setProductionCompanies(movie.getProductionCompanies());
            foundMovie.setAdult(movie.isAdult());

            movieRepository.save(foundMovie);
        }

        return Optional.of(foundMovie);

    }

    @Override
    public Boolean deleteById(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
