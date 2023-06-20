package org.api.movieApi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.api.movieApi.entities.Movie;
import org.api.movieApi.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Movie> listMovies(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) Double runtime,
                                  @RequestParam(required = false) String language,
                                  @RequestParam(required = false) String genre,
                                  @RequestParam(required = false) Boolean adult) {

        return movieService.listMovies(title, runtime, language, genre, adult);
    }

    @PostMapping(value = PATH)
    public ResponseEntity saveNewMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieService.saveNewMovie(movie);

        return new ResponseEntity(HttpStatus.CREATED);
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
