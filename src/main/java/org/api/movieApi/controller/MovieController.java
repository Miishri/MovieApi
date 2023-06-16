package org.api.movieApi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.api.movieApi.entities.Movie;
import org.api.movieApi.services.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MovieController {

    public static final String PATH = "/api/movie";
    public static final String ID_PATH = PATH +  "/{movieId}";

    private final MovieService movieService;

    @GetMapping(value = ID_PATH)
    public Movie getMovieById(@PathVariable("movieId") Long movieId) {
        return movieService.getMovieById(movieId).orElseThrow(NotFoundException::new);
    }

    @GetMapping(value = PATH)
    public Page<Movie> listMovies(@RequestParam(required = false) String movieTitle,
                                  @RequestParam(required = false) Double runtime,
                                  @RequestParam(required = false) String originalLanguage,
                                  @RequestParam(required = false) String genre,
                                  @RequestParam(required = false) Boolean adult,
                                  @RequestParam(required = false) Integer pageNumber,
                                  @RequestParam(required = false) Integer pageSize) {

        return movieService.listMovies(movieTitle, runtime, originalLanguage, genre, adult, pageNumber, pageSize);
    }

    @PostMapping(value = PATH)
    public ResponseEntity saveNewMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieService.saveNewMovie(movie);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", PATH + "/" + savedMovie.getId());

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = ID_PATH)
    public ResponseEntity updateMovieById(@PathVariable("movieId") Long movieId, @RequestBody Movie movie) {
        if (movieService.updateMovieById(movieId, movie).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = ID_PATH)
    public ResponseEntity deleteMovieById(@PathVariable("movieId") Long movieId) {
        if (! movieService.deleteById(movieId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
