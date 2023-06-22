package org.api.movieApi.services;

import lombok.RequiredArgsConstructor;
import org.api.movieApi.controller.HttpNotFoundException;
import org.api.movieApi.entities.Movie;
import org.api.movieApi.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MovieServiceJPA implements MovieService {

    private final MovieRepository movieRepository;
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    @Override
    public Page<Movie> listMovies(Integer pageNumber, Integer pageSize) {
        return movieRepository.findAll(buildPageRequest(pageNumber, pageSize));
    }
    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc("title"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
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
