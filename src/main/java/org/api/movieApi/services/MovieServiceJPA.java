package org.api.movieApi.services;

import lombok.RequiredArgsConstructor;
import org.api.movieApi.entities.Movie;
import org.api.movieApi.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Service
public class MovieServiceJPA implements MovieService {

    private final MovieRepository movieRepository;
    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_PAGE_SIZE = 100;
    @Override
    public Page<Movie> listMovies(String movieTitle,
                                  Double runtime,
                                  String originalLanguage,
                                  String genre,
                                  Boolean adult,
                                  Integer pageNumber,
                                  Integer pageSize) {

        PageRequest pageRequest = pageRequestBuilder(pageNumber, pageSize);

        Page<Movie> moviePage;

        if (StringUtils.hasText(movieTitle)) {
            moviePage = movieRepository.findAllByTitle(movieTitle, pageRequest);
        } else if (runtime != null) {
            moviePage = movieRepository.findAllByRuntime(runtime, pageRequest);
        } else if (StringUtils.hasText(originalLanguage)) {
            moviePage = movieRepository.findAllByOriginalLanguage(originalLanguage, pageRequest);
        } else if (adult != null) {
            moviePage = movieRepository.findAllByAdult(adult, pageRequest);
        } else {
            moviePage = movieRepository.findAll(pageRequest);
        }


        return moviePage;
    }

    public PageRequest pageRequestBuilder(Integer pageNumber,
                                          Integer pageSize) {
        int pageQueryNumber;
        int pageQuerySize;

        if (pageNumber != null && pageNumber > 0) {
            pageQueryNumber = pageNumber - 1;
        } else {
            pageQueryNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            pageQuerySize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 45462) {
                pageQuerySize = 1000;
            } else {
                pageQuerySize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc("title"));

        return PageRequest.of(pageQueryNumber, pageQuerySize, sort);
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

            //atomicMovie.set(Optional.of(movieRepository.save(foundMovie)));
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
