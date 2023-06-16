package org.api.movieApi.services;

import lombok.RequiredArgsConstructor;
import org.api.movieApi.entities.Movie;
import org.api.movieApi.repository.*;
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
    private final GenreRepository genreRepository;
    private final ProductionCompaniesRepository companiesRepository;
    private final ProductionCountriesRepository countriesRepository;
    private final SpokenLanguagesRepository spokenLanguagesRepository;
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

            foundMovie.setTitle(movie.getTitle());
            foundMovie.setOriginalTitle(movie.getOriginalTitle());
            foundMovie.setOverview(movie.getOverview());

            foundMovie.setGenres(movie.getGenres());
            foundMovie.getGenres().forEach(genre -> {
                genre.setMovie(foundMovie);
                genreRepository.save(genre);
            });

            foundMovie.setReleaseDate(movie.getReleaseDate());
            foundMovie.setRuntime(movie.getRuntime());
            foundMovie.setOriginalLanguage(movie.getOriginalLanguage());

            foundMovie.setSpokenLanguages(movie.getSpokenLanguages());
            foundMovie.getSpokenLanguages().forEach(spoken -> {
                spoken.setMovie(foundMovie);
                spokenLanguagesRepository.save(spoken);
            });

            foundMovie.setPopularity(movie.getPopularity());
            foundMovie.setStatus(movie.getStatus());
            foundMovie.setVoteAverage(movie.getVoteAverage());
            foundMovie.setVoteCount(movie.getVoteCount());
            foundMovie.setBudget(movie.getBudget());
            foundMovie.setRevenue(movie.getRevenue());
            foundMovie.setHomepage(movie.getHomepage());
            foundMovie.setImdbId(movie.getImdbId());

            foundMovie.setProductionCompanies(movie.getProductionCompanies());
            foundMovie.getProductionCompanies().forEach(production -> {
                production.setMovie(foundMovie);
                companiesRepository.save(production);
            });

            foundMovie.setProductionCountries(movie.getProductionCountries());
            foundMovie.getProductionCountries().forEach(country -> {
                country.setMovie(foundMovie);
                countriesRepository.save(country);
            });

            foundMovie.setVideo(movie.getVideo());
            foundMovie.setAdult(movie.getAdult());

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
