package org.api.movieApi.services;

import lombok.RequiredArgsConstructor;
import org.api.movieApi.entities.Movie;
import org.api.movieApi.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Service
public class MovieServiceJPA implements MovieService {

    private final MovieRepository movieRepository;
    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_PAGE_SIZE = 100;
    @Override
    public List<Movie> listMovies(String movieTitle,
                                  Double runtime,
                                  String language,
                                  String genre,
                                  Boolean adult) {


        List<Movie> moviePage;

        if (StringUtils.hasText(movieTitle)) {
            moviePage = movieRepository.findAllByTitle(movieTitle);
        } else if (runtime != null) {
            moviePage = movieRepository.findAllByRuntime(runtime);
        } else if (StringUtils.hasText(language)) {
            moviePage = movieRepository.findAllByOriginalLanguage(language);
        } else if (adult != null) {
            moviePage = movieRepository.findAllByAdult(adult);
        } else {
            moviePage = movieRepository.findAll();
        }


        return moviePage;
    }

    @Override
    public Optional<Movie> getMovieById(Long id) {
        return Optional.of(movieRepository.findById(id)).orElse(null);
    }

    @Override
    public Movie saveNewMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Optional<Movie> updateMovieById(Long id, Movie movie) {

        AtomicReference<Optional<Movie>> atomicMovie = new AtomicReference<>();

        movieRepository.findById(id).ifPresentOrElse(foundMovie -> {

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
            foundMovie.setVideo(movie.getVideo());
            foundMovie.setAdult(movie.getAdult());

            movieRepository.save(foundMovie);

            atomicMovie.set(Optional.of(foundMovie));
        }, () -> {
            atomicMovie.set(Optional.empty());
        });

        return atomicMovie.get();
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
